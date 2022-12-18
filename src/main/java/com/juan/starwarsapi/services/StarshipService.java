package com.juan.starwarsapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.juan.starwarsapi.entities.People;
import com.juan.starwarsapi.entities.Starship;
import com.juan.starwarsapi.repositories.StarshipRepository;
import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StarshipService {

    @Autowired
    private StarshipRepository starshipRepository;

    public void loadData() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        RestTemplate restTemplate = new RestTemplate();
        String next = null;
        do {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            next==null?"https://swapi.dev/api/starships/":next,
                            String.class);
            String starships = response.getBody();
            JsonElement jsonElement = gson.fromJson(starships, JsonElement.class);
            JsonObject json = jsonElement.getAsJsonObject();
            for (JsonElement o : json.get("results").getAsJsonArray()) {
                Starship starship = gson.fromJson(o, Starship.class);
                List<People> pilots = new ArrayList<>();
                for(JsonElement pilotUrl : o.getAsJsonObject().get("pilots").getAsJsonArray()){
                    People pilot = restTemplate.getForObject(pilotUrl.getAsString(), People.class);
                    pilots.add(pilot);
                }
                starship.setPilots(pilots);
                starshipRepository.save(starship);
            }
            try {
                next = json.get("next").getAsString();
            }catch (UnsupportedOperationException e){
                next = null;
            }
        }while (next != null);
    }
}
