package com.juan.starwarsapi.controllers;

import com.google.gson.Gson;
import com.juan.starwarsapi.entities.CreateMissionRequest;
import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class MissionController {

    @Autowired
    private MissionService missionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String options(){
        StringBuilder sb = new StringBuilder();
        sb.append("\"create missions - POST\": \"http://localhost:8090/missions/\"");
        sb.append("\"list missions - GET\": \"http://localhost:8090/missions/\"");
        sb.append("\"actual\": \"http://localhost:8090/\"");
        return new Gson().toJson(sb.toString());
    }

    @RequestMapping(value = "/missions", method = RequestMethod.GET)
    public String listAll( @RequestParam(value="", required=false) String search, Pageable pageable){
        Page<Mission> missionPage = new PageImpl<Mission>(new LinkedList<Mission>());
        missionPage = missionService.getAllMissions(pageable, search);
        StringBuilder sb = new StringBuilder();
        sb.append("\"count\": \""+ missionPage.getContent().size() +"\"");
        sb.append("\"actual\": \"http://localhost:8090/missions/\"");
        StringBuilder sbMission = new StringBuilder();
        sbMission.append("[");
        for(Mission m : missionPage.getContent()){
            sbMission.append(m.toString());
        }
        sbMission.append("]");
        sb.append("\"results\":" + sbMission);
        return new Gson().toJson(sb.toString());
    }

    @RequestMapping(value = "/missions", method = RequestMethod.POST)
    public String create(@RequestBody CreateMissionRequest missionRequest){
        StringBuilder sb = new StringBuilder();
        sb.append("\"actual\": \"http://localhost:8090/missions/\"");
        sb.append("\"results\":" + missionService.addMission(missionRequest));
        return new Gson().toJson(sb.toString());
    }
}

