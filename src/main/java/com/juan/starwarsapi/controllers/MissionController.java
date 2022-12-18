package com.juan.starwarsapi.controllers;

import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MissionController {

    @Autowired
    private MissionService missionService;

    @RequestMapping(value = "/missions", method = RequestMethod.GET)
    public List<Mission> listAll(){
        return missionService.getAllMissions();
    }

    @RequestMapping(value = "/missions", method = RequestMethod.POST)
    public Mission create(@Validated Mission mission, BindingResult result){
        if(!result.hasErrors()) {
            missionService.addMission(mission);
            return mission;
        }
        return null;
    }
}

