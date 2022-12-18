package com.juan.starwarsapi.repositories;

import com.juan.starwarsapi.entities.People;
import com.juan.starwarsapi.entities.Starship;
import org.springframework.data.repository.CrudRepository;

public interface PeopleRepository extends CrudRepository<People, Long> {
}
