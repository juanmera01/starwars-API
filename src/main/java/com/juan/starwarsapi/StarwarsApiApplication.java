package com.juan.starwarsapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juan.starwarsapi.services.MissionService;
import com.juan.starwarsapi.services.PeopleService;
import com.juan.starwarsapi.services.PlanetService;
import com.juan.starwarsapi.services.StarshipService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StarwarsApiApplication {

    @Autowired
    public StarshipService starshipService;
    @Autowired
    public PeopleService peopleService;
    @Autowired
    public PlanetService planetService;
    @Autowired
    public MissionService missionService;

    @PostConstruct
    public void postcontruct() {
        starshipService.loadData();
        peopleService.loadData();
        planetService.loadData();
        missionService.loadTestData();
    }


    public static void main(String[] args) {
        SpringApplication.run(StarwarsApiApplication.class, args);
    }

}
