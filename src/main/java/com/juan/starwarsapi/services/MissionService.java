package com.juan.starwarsapi.services;

import com.juan.starwarsapi.entities.*;
import com.juan.starwarsapi.entities.binder.Binder;
import com.juan.starwarsapi.repositories.MissionRepository;
import com.juan.starwarsapi.repositories.PeopleRepository;
import com.juan.starwarsapi.repositories.PlanetRepository;
import com.juan.starwarsapi.repositories.StarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class MissionService {

    @Autowired
    public MissionRepository missionRepository;
    @Autowired
    private StarshipRepository starshipRepository;
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private PlanetRepository planetRepository;


    public Object addMission(CreateMissionRequest missionRequest){
        List<String> errors = validateParams(missionRequest);
        if(!errors.isEmpty())
            return errors;
        Mission mission = new Mission();
        missionRepository.save(mission);
        mission.setInitialDate(missionRequest.initialDate);
        mission.setCrew(missionRequest.crew);
        for(People p : missionRequest.people_ids
                .stream().map(id -> peopleRepository.findById(id).orElse(null)).toList()) {
            mission.getCaptains().add(p);
        }
        Starship starship = starshipRepository.findById(missionRequest.starship_id).orElse(null);
        mission.setStarShips(starship);
        for(Planet p : missionRequest.planet_ids
                .stream().map(id -> planetRepository.findById(id).orElse(null)).toList()) {
            mission.getPlanets().add(p);
        }
        mission.setDuration(calculateDuration(mission));
        errors = validate(mission);
        if(errors.size() == 0) {
            mission.setDuration(calculateDuration(mission));
            return mission;
        }else{
            missionRepository.delete(mission);
            return errors;
        }
    }

    /**
     * Make the first layer validations
     * @param missionRequest
     * @return errors
     */
    private List<String> validateParams(CreateMissionRequest missionRequest) {
        List<String> errors = new ArrayList<>();
        if(missionRequest.initialDate == null)
            errors.add("Error.create.mission.initialDate");
        if(starshipRepository.findById(missionRequest.starship_id).isEmpty())
            errors.add("Error.create.mission.starship.empty");
        if(missionRequest.people_ids.size() < 1)
            errors.add("Error.create.mission.captains.empty");
        if(missionRequest.planet_ids.size() < 1)
            errors.add("Error.create.mission.planets.empty");
        for(long id : missionRequest.people_ids)
            if(peopleRepository.findById(id).isEmpty())
                errors.add("Error.create.mission.captains.nonexists");
        for(long id : missionRequest.planet_ids)
            if(planetRepository.findById(id).isEmpty())
                errors.add("Error.create.mission.planet.nonexists");
        if(missionRequest.crew < 1)
            errors.add("Error.create.mission.crew.empty");
        return errors;
    }

    /**
     * @return All Missions
     */
    public List<Mission> getAllMissions(){
        return missionRepository.findAll();
    }

    /**
     * @param id
     * @return mission
     */
    public Optional<Mission> getMissionById(long id){
        return missionRepository.findById(id);
    }

    /**
     * @param mission
     * @return duration
     */
    private int calculateDuration(Mission mission) {
        int sumDiameters = mission.getPlanets().stream().map(Planet::getDiameter).reduce(Integer::sum).get();
        int hours = 0;
        int kmPerHour = (mission.getCaptains().size() * 100) + (mission.getCrew() * 10);
        if(!mission.getCaptains().isEmpty()) {
            if(sumDiameters % kmPerHour != 0)
                hours = sumDiameters / kmPerHour + 1;
            else
                hours = sumDiameters / kmPerHour;
        }else
            return -1;
        return Math.max(hours, 1);
    }

    /**
     * Make the second layer validations
     * @param mission
     * @return errors
     */
    public List<String> validate(Mission mission){
        List<String> errors = new ArrayList<>();

       if(mission.getStarShips().getPilots().size() > 0){  // there is at least one last pilot
            Set<People> intersection = mission.getStarShips().getPilots();
            intersection.retainAll(mission.getCaptains());
            if(intersection.isEmpty()){
                errors.add("Error.create.mission.pilots");
            }
        }
        // min crew
        if(mission.getCaptains().size()+mission.getCrew() < mission.getStarShips().getCrew())
            errors.add( "Error.create.mission.crew.size");
        // max crew + passengers
        if(mission.getCaptains().size()+mission.getCrew() >
                mission.getStarShips().getCrew()+mission.getStarShips().getPassengers()
                && mission.getStarShips().getPassengers() > 0 )
            errors.add("Error.create.mission.passengers.size");
        if(checkCaptains(mission.getCaptains(), mission)) // any captain in other mission
            errors.add( "Error.create.mission.captains.busy");
        return errors;
    }

    /**
     * Check if the captain is busy in other mission that is not the one passed throw params
     * @param captains
     * @param mission
     * @return boolean
     */
    private boolean checkCaptains(Set<People> captains, Mission mission) { // TODO hacerlo en consulta jpql eficiente
        for(People p : captains){
            for(Mission m : missionRepository.findMissionByCaptain(mission.getId()))
                if(m.getCaptains().contains(p))
                    return true;
        }
        return false;
    }

    /**
     * Add manually a mission
     */
    public void loadTestData(){
        CreateMissionRequest m = new CreateMissionRequest();
        m.initialDate = LocalDateTime.now();
        m.crew = 6;
        m.starship_id = 3L;
        m.people_ids = List.of(3L);
        m.planet_ids = List.of(3L);
        addMission(m);
    }
}
