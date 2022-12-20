package com.juan.starwarsapi.repositories;

import com.juan.starwarsapi.entities.Planet;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<Planet, Long> {
}
