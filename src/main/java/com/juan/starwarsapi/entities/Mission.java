package com.juan.starwarsapi.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime initialDate;
    @ManyToOne
    @JoinColumn(name = "starship_id")
    private Starship starShip;
    @OneToMany(mappedBy = "people_id")
    private List<People> captains = new ArrayList<>();
    @OneToMany(mappedBy = "planet_id")
    private List<Planet> planets = new ArrayList<>();
    private int crew;
    private int duration;

    public Mission(){}

    public Mission(long id, LocalDateTime initialDate, Starship starShip, List<People> captains, List<Planet> planets, int crew) {
        this.id = id;
        this.initialDate = initialDate;
        this.starShip = starShip;
        this.captains = captains;
        this.planets = planets;
        this.crew = crew;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public Starship getStarShips() {
        return starShip;
    }

    public List<People> getCaptains() {
        return captains;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public int getCrew() {
        return crew;
    }

    public int getDuration() {
        return duration;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public void setStarShips(Starship starShip) {
        this.starShip = starShip;
    }

    public void setCaptains(List<People> captains) {
        this.captains = captains;
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }

    public void setCrew(int crew) {
        this.crew = crew;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", initialDate=" + initialDate +
                ", starShip=" + starShip +
                ", captains=" + captains +
                ", planets=" + planets +
                ", crew=" + crew +
                ", duration=" + duration + " (hours)" +
                '}';
    }
}
