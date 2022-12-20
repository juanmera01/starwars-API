package com.juan.starwarsapi.entities.binder;

import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.entities.People;
import com.juan.starwarsapi.entities.Planet;
import com.juan.starwarsapi.entities.Starship;

public class Binder {

    public static void bind(Mission mission, People person){
        person.setMission(mission);
        mission.getCaptains().add(person);
    }

    public static void bind(Mission mission, Starship starship){
        mission.setStarShip(starship);
        starship.setMissions(mission);
    }

    public static void bind(Mission mission, Planet planet){
        planet.setMission(mission);
        mission.getPlanets().add(planet);
    }

    public static void bind(Starship starship, People person){
        starship.getPilots().add(person);
        person.getStarships().add(starship);
    }
}
