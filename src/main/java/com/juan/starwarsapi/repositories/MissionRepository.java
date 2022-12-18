package com.juan.starwarsapi.repositories;

import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.entities.People;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MissionRepository extends CrudRepository<Mission, Long> {

    List<Mission> findAll();

    @Query("SELECT m from Mission m where ?1 in (m.captains)")
    List<Mission> findMissionByCaptain(People captain);
}
