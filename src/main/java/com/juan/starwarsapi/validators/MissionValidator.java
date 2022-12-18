package com.juan.starwarsapi.validators;

import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.entities.People;
import com.juan.starwarsapi.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class MissionValidator implements Validator {

    @Autowired
    private MissionService missionService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Mission.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Mission mission = (Mission)target;

        if(mission.getInitialDate() == null)    // one a
            errors.rejectValue("date", "Error.create.mission.date.empty");
        if(mission.getStarShips() == null)  // one b
            errors.rejectValue("starship", "Error.create.mission.starship.empty");
        if(mission.getCaptains().size() > 0)    // at least one c
            errors.rejectValue("captains", "Error.create.mission.captains.empty");
        if(mission.getPlanets().size() > 0) // at least one e
            errors.rejectValue("planets", "Error.create.mission.planets.empty");
        if(mission.getCrew() > 0)   // d bigger than 0
            errors.rejectValue("crew", "Error.create.mission.crew.empty");
        if(mission.getStarShips().getPilots().size() > 0){  // there is at least one last pilot
            List<People> intersection = mission.getStarShips().getPilots();
            intersection.retainAll(mission.getCaptains());
            if(intersection.isEmpty()){
                errors.rejectValue("pilots", "Error.create.mission.pilots");
            }
        }
        // min crew
        if(mission.getCaptains().size()+mission.getCrew() < mission.getStarShips().getCrew())
            errors.rejectValue("crew", "Error.create.mission.crew.size");
        // max crew + passagers
        if(mission.getCaptains().size()+mission.getCrew() >
                mission.getStarShips().getCrew()+mission.getStarShips().getPassengers())
            errors.rejectValue("passengers", "Error.create.mission.passengers.size");
        if(missionService.checkCaptains(mission.getCaptains())) // any captain in other mission
            errors.rejectValue("captains", "Error.create.mission.captains.busy");
    }
}
