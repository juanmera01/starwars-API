package com.juan.starwarsapi.repositories;

import com.juan.starwarsapi.entities.Mission;
import com.juan.starwarsapi.entities.Starship;
import org.springframework.data.repository.CrudRepository;

public interface StarshipRepository extends CrudRepository<Starship, Long> {
}
