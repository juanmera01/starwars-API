package com.juan.starwarsapi.entities;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public class CreateMissionRequest {

    public LocalDateTime initialDate;
    public long starship_id;
    public List<Long> people_ids;
    public List<Long> planet_ids;
    public int crew;
}
