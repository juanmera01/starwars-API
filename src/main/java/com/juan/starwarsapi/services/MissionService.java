package com.juan.starwarsapi.services;

import com.juan.starwarsapi.entities.*;
import com.juan.starwarsapi.entities.binder.Binder;
import com.juan.starwarsapi.repositories.MissionRepository;
import com.juan.starwarsapi.repositories.PeopleRepository;
import com.juan.starwarsapi.repositories.PlanetRepository;
import com.juan.starwarsapi.repositories.StarshipRepository;
import com.juan.starwarsapi.requests.CreateMissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class MissionService {

    public MissionRepository missionRepository;
    @Autowired
    private StarshipRepository starshipRepository;
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private PlanetRepository planetRepository;

    private final List<Mission> missionsToOrdered = new ArrayList<>();

    @Autowired
    public MissionService(MissionRepository missionRepository){
        this.missionRepository = missionRepository;
        missionsToOrdered.addAll(missionRepository.findAll());
    }

    /**
     * Validate and create a mission
     * @param missionRequest
     * @return mission created
     */
    public Mission addMission(CreateMissionRequest missionRequest){

        List<String> errors = validateParams(missionRequest);
        if(!errors.isEmpty())
            return null;
        Mission mission = new Mission();
        missionRepository.save(mission);
        mission.setInitialDate(missionRequest.initialDate);
        mission.setCrew(missionRequest.crew);
        mission.setReward(missionRequest.reward);

        Set<People> captains = new HashSet<>(missionRequest.people_ids
                .stream().map(id -> peopleRepository.findById(id).orElse(null)).toList());
        Starship starship = starshipRepository.findById(missionRequest.starship_id).orElse(null);
        Set<Planet> planets = new HashSet<>(missionRequest.planet_ids
                .stream().map(id -> planetRepository.findById(id).orElse(null)).toList());

        errors = validate(mission, starship, captains);

        if(errors.size() == 0) {
            captains.forEach(c -> Binder.bind(mission, c));
            Binder.bind(mission, starship);
            planets.forEach(p -> Binder.bind(mission, p));
            mission.setDuration(calculateDuration(mission));
            missionsToOrdered.add(mission);
            return mission;
        }else{
            missionRepository.delete(mission);
            return null;
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
        if(missionRequest.people_ids == null || missionRequest.people_ids.size() < 1)
            errors.add("Error.create.mission.captains.empty");
        if(missionRequest.planet_ids == null || missionRequest.planet_ids.size() < 1)
            errors.add("Error.create.mission.planets.empty");
        if(missionRequest.planet_ids != null && missionRequest.people_ids != null) {
            for (long id : missionRequest.people_ids)
                if (peopleRepository.findById(id).isEmpty())
                    errors.add("Error.create.mission.captains.nonexists");
            for (long id : missionRequest.planet_ids)
                if (planetRepository.findById(id).isEmpty())
                    errors.add("Error.create.mission.planet.nonexists");
        }
        if(missionRequest.crew < 1)
            errors.add("Error.create.mission.crew.empty");
        return errors;
    }

    /**
     * @return All Missions paginated
     */
    public Page<Mission> getAllMissions(Pageable pageable, String captainNames){
        if(captainNames.equals(""))
            return missionRepository.findAll(pageable);
        else
            return missionRepository.findMissionByCaptainNames(pageable, '%'+captainNames+'%');
    }

    /**
     * Get mission by id
     * @param id
     * @return mission (or null if it does not exists)
     */
    public Mission getMissionById(long id){
        return missionRepository.findById(id).orElse(null);
    }

    /**
     * Calculate the mission duration by the planets, the crew and the captains
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
    public List<String> validate(Mission mission, Starship starship, Set<People> captains){
        List<String> errors = new ArrayList<>();

       if(starship.getPilots().size() > 0){  // there is at least one last pilot
            Set<People> intersection = starship.getPilots();
            intersection.retainAll(captains);
            if(intersection.isEmpty()){
                errors.add("Error.create.mission.pilots");
            }
        }
        // min crew
        if(captains.size() + mission.getCrew() < starship.getCrew())
            errors.add( "Error.create.mission.crew.size");
        // max crew + passengers
        if(captains.size() + mission.getCrew() > starship.getCrew() + starship.getPassengers()
                && starship.getPassengers() > 0 )
            errors.add("Error.create.mission.passengers.size");
        if(checkCaptains(captains, mission)) // any captain in other mission
            errors.add( "Error.create.mission.captains.busy");
        return errors;
    }

    /**
     * Check if the captain is busy in other mission that is not the one passed throw params
     * @param captains
     * @param mission
     * @return boolean
     */
    private boolean checkCaptains(Set<People> captains, Mission mission) {
        for(People p : captains){
            if(!missionRepository.findMissionByCaptain(p, mission.getId()).isEmpty())
                return true;
        }
        return false;
    }

    /**
     * Get the next mission ordered by the criteria recibed through params
     * @param comparator (to order the missions)
     * @return mission
     */
    public Mission getMissionBy(Comparator<Mission> comparator) {
        if(missionsToOrdered.isEmpty())
            return null;
        PriorityQueue<Mission> queue = new PriorityQueue<>(missionsToOrdered.size(), comparator);
        List<Mission> missionsNonTransient = new ArrayList<>();
        for(Mission m : missionsToOrdered){
            missionsNonTransient.add(missionRepository.findById(m.getId()).get());
        }
        queue.addAll(missionsNonTransient);
        Mission selected = queue.poll();
        missionsToOrdered.remove(selected);
        return selected;
    }

    /**
     * Add manually a mission
     */
    public void loadTestData(){
        CreateMissionRequest m1 = new CreateMissionRequest();
        m1.initialDate = LocalDateTime.now();
        m1.crew = 25;
        m1.starship_id = 3L;
        m1.people_ids = List.of(3L);
        m1.planet_ids = List.of(3L);
        m1.reward = 10000.00;
        addMission(m1);

        CreateMissionRequest m2 = new CreateMissionRequest();
        m2.initialDate = LocalDateTime.now();
        m2.crew = 50000;
        m2.starship_id = 2L;
        m2.people_ids = List.of(2L);
        m2.planet_ids = List.of(2L);
        m2.reward = 15000.00;
        addMission(m2);

        CreateMissionRequest m3 = new CreateMissionRequest();
        m3.initialDate = LocalDateTime.now();
        m3.crew = 342960;
        m3.starship_id = 4L;
        m3.people_ids = List.of(4L);
        m3.planet_ids = List.of(4L);
        m3.reward = 20000.00;
        addMission(m3);

        CreateMissionRequest m4 = new CreateMissionRequest();
        m4.initialDate = LocalDateTime.now();
        m4.crew = 342960;
        m4.starship_id = 4L;
        m4.people_ids = List.of(7L);
        m4.planet_ids = List.of(4L);
        m4.reward = 9000.00;
        addMission(m4);

        CreateMissionRequest m5 = new CreateMissionRequest();
        m5.initialDate = LocalDateTime.now();
        m5.crew = 342960;
        m5.starship_id = 4L;
        m5.people_ids = List.of(10L);
        m5.planet_ids = List.of(4L);
        m5.reward = 9000.00;
        addMission(m5);

        CreateMissionRequest m6 = new CreateMissionRequest();
        m6.initialDate = LocalDateTime.now();
        m6.crew = 342960;
        m6.starship_id = 4L;
        m6.people_ids = List.of(11L);
        m6.planet_ids = List.of(4L);
        m6.reward = 9000.00;
        addMission(m6);
    }

}
