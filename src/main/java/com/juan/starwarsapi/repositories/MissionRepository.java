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

    @Query("SELECT m FROM Mission m WHERE ?1 IN (m.captains) AND m.id <> ?2")
    List<Mission> findMissionByCaptain(People person, long mission_id);

    @Query("SELECT m FROM Mission m join m.captains c WHERE lower(c.name) like lower (?1)")
    Page<Mission> findMissionByCaptainNames(Pageable pageable, String names);
}
