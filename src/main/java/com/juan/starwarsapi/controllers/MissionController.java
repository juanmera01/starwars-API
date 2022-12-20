package com.juan.starwarsapi.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.juan.starwarsapi.entities.CreateMissionRequest;
import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.services.MissionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class MissionController {

    @Autowired
    private MissionService missionService;

    /**
     * Get the API end-points
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> options(){
        String res = "{" +
                "\"create missions-POST\": \"http://localhost:8090/missions\"," +
                "\"list missions-GET\": \"http://localhost:8090/missions\"," +
                "\"list mission-GET\": \"http://localhost:8090/missions/{id}\"," +
                "\"actual\": \"http://localhost:8090/\"" +
                "}";
        return ResponseEntity.ok().body(res);
    }

    /**
     *  Get all missions
     * @param search (to search by captains names)
     * @param pageable
     * @return page<mission>
     */
    @RequestMapping(value = "/missions", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listAll
            (@RequestParam Optional<String> search, Pageable pageable) {
        Page<Mission> missionPage = new PageImpl<Mission>(new LinkedList<Mission>());
        missionPage = missionService.getAllMissions(pageable, search.orElse(""));
        StringBuilder sbMission = new StringBuilder();
        sbMission.append("[");
        for(Mission m : missionPage.getContent()){
            if(missionPage.getContent().indexOf(m) != missionPage.getContent().size()-1)
                sbMission.append(m.toString()).append(",");
            else
                sbMission.append(m.toString());
        }
        sbMission.append("]");

        return ResponseEntity.ok().body(sbMission.toString());
    }

    /**
     * Create a mission
     * @param missionRequest
     * @return created mission
     */
    @RequestMapping(value = "/missions", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, headers="Accept=application/json")
    public ResponseEntity<String> create(@RequestBody CreateMissionRequest missionRequest){

        Mission m = missionService.addMission(missionRequest);
        if(m == null)
            return ResponseEntity.notFound().build();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(m.getId())
                .toUri();

        return ResponseEntity.created(uri).body(m.toString());
    }

    /**
     *  Get mission by id
     * @param id
     * @return mission
     */
    @RequestMapping(value = "/missions/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOneMission(@PathVariable long id){
        Mission m = missionService.getMissionById(id);
        if(m == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(m.toString());
    }
}

