package com.juan.starwarsapi.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime initialDate;

    @OneToOne
    @JoinColumn(name = "starship_id")
    private Starship starShip;

    @OneToMany(mappedBy = "mission", cascade=CascadeType.ALL)
    private Set<People> captains = new HashSet<>();
    @OneToMany(mappedBy = "mission", cascade=CascadeType.ALL)
    private Set<Planet> planets = new HashSet<>();

    private int crew;
    private int duration;

    public Mission(){}

    public Mission(long id, LocalDateTime initialDate, Starship starShip, Set<People> captains, Set<Planet> planets, int crew) {
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

    public Set<People> getCaptains() {
        return captains;
    }

    public Set<Planet> getPlanets() {
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

    public void setCaptains(Set<People> captains) {
        this.captains = captains;
    }

    public void setPlanets(Set<Planet> planets) {
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
        return "{" +
                "\"id\": " + "\""+id +"\""+
                "\"initialDate\": " + "\""+initialDate +"\"" +
                "\"endDate\": " + "\""+initialDate.plusHours(duration) +"\"" +
                "\"starship\": " + starShip +
                "\"captains\": " + captains.stream().map(c -> c.toString())  +
                "\"planets\": " +planets.stream().map(p -> p.toString())  +
                "\"crew\": " + "\""+crew +"\"" +
                "\"durationHours\": " + "\""+duration +"\"" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return getId() == mission.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
