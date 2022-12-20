package com.juan.starwarsapi.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.juan.starwarsapi.entities.People;
import com.juan.starwarsapi.entities.Planet;
import com.juan.starwarsapi.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Transactional
@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    /**
     * Get a planet by id
     * @param id
     * @return planet (or null if it does not exists)
     */
    public Planet getById(long id){
        Optional<Planet> oplanet = planetRepository.findById(id);
        return oplanet.orElse(null);
    }

    /**
     * Make a request to starwars API to load, parse and save planets data
     * URI: https://swapi.dev/api/planets/{page}
     */
    public void loadData(){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        RestTemplate restTemplate = new RestTemplate();
        String next = null;
        do {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            next==null?"https://swapi.dev/api/planets/":next,
                            String.class);
            String starships = response.getBody();
            JsonElement jsonElement = gson.fromJson(starships, JsonElement.class);
            JsonObject json = jsonElement.getAsJsonObject();
            for (JsonElement o : json.get("results").getAsJsonArray()) {
                Planet planet = gson.fromJson(o, Planet.class);
                planetRepository.save(planet);
            }
            try {
                next = json.get("next").getAsString();
            }catch (UnsupportedOperationException e){
                next = null;
            }
        }while (next != null);
    }
}
