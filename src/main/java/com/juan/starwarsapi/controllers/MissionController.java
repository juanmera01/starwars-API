package com.juan.starwarsapi.controllers;

import com.juan.starwarsapi.requests.CreateMissionRequest;
import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.services.MissionService;
import com.juan.starwarsapi.utils.MissionRewardComparator;
import com.juan.starwarsapi.utils.MissionRewardPerHourComparator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.LinkedList;
import java.util.Objects;

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
                "\"list missions-GET\": \"http://localhost:8090/missions?page=1&size=5\"," +
                "\"mission-GET\": \"http://localhost:8090/missions/{id}\"," +
                "\"recommendator-reward\": \"http://localhost:8090/missions/next?criteria=reward\"," +
                "\"recommendator-rewardPerHour\": \"http://localhost:8090/missions/next?criteria=rewardPerHour\"," +
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
    @RequestMapping(value = "/missions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listAll
            (Pageable pageable, @RequestParam(required = false, defaultValue = "") String search) {
        Page<Mission> missionPage = new PageImpl<Mission>(new LinkedList<Mission>());
        missionPage = missionService.getAllMissions(pageable, search);
        StringBuilder sbMission = new StringBuilder();
        HttpServletRequest request =
                ((ServletRequestAttributes)(Objects.requireNonNull(RequestContextHolder.getRequestAttributes())))
                        .getRequest();
        sbMission.append("{");
        sbMission.append("\"current\":\""+ request.getRequestURL().toString() + "?" + request.getQueryString() + "\",");
        sbMission.append("\"results\":[");
        for(Mission m : missionPage.getContent()){
            if(missionPage.getContent().indexOf(m) != missionPage.getContent().size()-1)
                sbMission.append(m.toString()).append(",");
            else
                sbMission.append(m.toString());
        }
        sbMission.append("]");
        sbMission.append("}");
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

    @RequestMapping(value = "/missions/next", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getNextMissionByPriority(@RequestParam String criteria){
        Mission m;
        switch (criteria) {
            case "reward":
                m = missionService.getMissionBy(new MissionRewardComparator());
                return ResponseEntity.ok(m != null?m.toString():"no missions left");
            case "rewardPerHour":
                m = missionService.getMissionBy(new MissionRewardPerHourComparator());
                return ResponseEntity.ok(m != null?m.toString():"no missions left");
            default: return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("The required parameter " + name + " is missing");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        // log ...
        return ResponseEntity.badRequest().build();
    }
}

