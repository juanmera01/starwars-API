package com.juan.starwarsapi.requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class CreateMissionRequest {

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    public LocalDateTime initialDate;
    public long starship_id;
    public List<Long> people_ids;
    public List<Long> planet_ids;
    public int crew;
    public double reward;
}
