package com.juan.starwarsapi.services;

import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.entities.People;
import com.juan.starwarsapi.entities.Planet;
import com.juan.starwarsapi.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionService {

    @Autowired
    public MissionRepository missionRepository;


    public void addMission(Mission mission){
        mission.setDuration(calculateDuration(mission));
        missionRepository.save(mission);
    }

    public List<Mission> getAllMissions(){
        return missionRepository.findAll();
    }

    public Optional<Mission> getMissionById(long id){
        return missionRepository.findById(id);
    }

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

    public boolean checkCaptains(List<People> captains) {
        for(People p : captains){
            if(!missionRepository.findMissionByCaptain(p).isEmpty())
                return true;
        }
        return false;
    }
}
