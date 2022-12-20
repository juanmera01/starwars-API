package com.juan.starwarsapi.repositories;

import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.entities.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MissionRepository extends CrudRepository<Mission, Long> {

    Page<Mission> findAll(Pageable pageable);

    @Query("SELECT m FROM Mission m WHERE m.id <> ?2 AND ?1 in (SELECT c FROM m.captains c)")
    List<Mission> findMissionByCaptain(People p, long mission_id);

    @Query("SELECT m FROM People p JOIN p.mission m WHERE LOWER(p.name) LIKE LOWER (?1)")
    Page<Mission> findMissionByCaptainNames(Pageable pageable, String names);
}
