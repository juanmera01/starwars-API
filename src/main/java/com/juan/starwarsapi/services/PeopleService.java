package com.juan.starwarsapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.juan.starwarsapi.entities.People;
import com.juan.starwarsapi.entities.Starship;
import com.juan.starwarsapi.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

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

                List<Starship> starships = new ArrayList<>();
                for(JsonElement starshipURL : o.getAsJsonObject().get("starships").getAsJsonArray()){
                    Starship starship = restTemplate.getForObject(starshipURL.getAsString(), Starship.class);
                    starships.add(starship);
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
