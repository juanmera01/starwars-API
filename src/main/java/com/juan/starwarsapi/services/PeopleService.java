package com.juan.starwarsapi.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.juan.starwarsapi.entities.People;
import com.juan.starwarsapi.entities.Starship;
import com.juan.starwarsapi.entities.binder.Binder;
import com.juan.starwarsapi.repositories.PeopleRepository;
import com.juan.starwarsapi.repositories.StarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Transactional
@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private StarshipRepository starshipRepository;

    /**
     * Get a person by id
     * @param id
     * @return people (or null if it does not exists)
     */
    public People getById(long id){
        Optional<People> opeople = peopleRepository.findById(id);
        return opeople.orElse(null);
    }

    /**
     * Make a request to starwars API to load, parse and save people data
     * URI: https://swapi.dev/api/people/{page}
     */
    public void loadData() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        RestTemplate restTemplate = new RestTemplate();
        String next = null;
        do {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            next==null?"https://swapi.dev/api/people/":next,
                            String.class);
            String peopleJson = response.getBody();
            JsonElement jsonElement = gson.fromJson(peopleJson, JsonElement.class);
            JsonObject json = jsonElement.getAsJsonObject();
            for (JsonElement o : json.get("results").getAsJsonArray()) {
                People person = gson.fromJson(o, People.class);

                Set<Starship> starships = new HashSet<>();
                for(JsonElement starshipURL : o.getAsJsonObject().get("starships").getAsJsonArray()){
                    Starship starship = restTemplate.getForObject(starshipURL.getAsString(), Starship.class);
                    starshipRepository.save(starship);
                    Binder.bind(starship, person);
                }
                person.setStarships(starships);
                peopleRepository.save(person);
            }
            try {
                next = json.get("next").getAsString();
            }catch (UnsupportedOperationException e){
                next = null;
            }
        }while (next != null);
    }
}
