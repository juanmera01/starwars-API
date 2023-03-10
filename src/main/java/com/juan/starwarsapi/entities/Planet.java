package com.juan.starwarsapi.entities;

import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;


@Entity
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long planet_id;

    @SerializedName("climate")
    @Expose
    private String climate;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("diameter")
    @Expose
    private String diameter;
    @SerializedName("edited")
    @Expose
    private String edited;
    @SerializedName("gravity")
    @Expose
    private String gravity;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("orbital_period")
    @Expose
    private String orbitalPeriod;
    @SerializedName("population")
    @Expose
    private String population;
    @SerializedName("rotation_period")
    @Expose
    private String rotationPeriod;
    @SerializedName("surface_water")
    @Expose
    private String surfaceWater;
    @SerializedName("terrain")
    @Expose
    private String terrain;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;


    /**
     * No args constructor for use in serialization
     *
     */
    public Planet() {
    }

    public Planet(String climate, String created, String diameter, String edited,
                  String gravity, String name, String orbitalPeriod,
                  String population, String rotationPeriod,
                  String surfaceWater, String terrain, String url) {
        super();
        this.climate = climate;
        this.created = created;
        this.diameter = diameter;
        this.edited = edited;
        this.gravity = gravity;
        this.name = name;
        this.orbitalPeriod = orbitalPeriod;
        this.population = population;
        this.rotationPeriod = rotationPeriod;
        this.surfaceWater = surfaceWater;
        this.terrain = terrain;
    }

    public String getClimate() {
        return climate;
    }
    public void setClimate(String climate) {
        this.climate = climate;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public int getDiameter() {
        return Integer.parseInt(diameter);
    }
    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }
    public String getEdited() {
        return edited;
    }
    public void setEdited(String edited) {
        this.edited = edited;
    }
    public String getGravity() {
        return gravity;
    }
    public void setGravity(String gravity) {
        this.gravity = gravity;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOrbitalPeriod() {
        return orbitalPeriod;
    }
    public void setOrbitalPeriod(String orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }
    public String getPopulation() {
        return population;
    }
    public void setPopulation(String population) {
        this.population = population;
    }
    public String getRotationPeriod() {
        return rotationPeriod;
    }
    public void setRotationPeriod(String rotationPeriod) {
        this.rotationPeriod = rotationPeriod;
    }
    public String getSurfaceWater() {
        return surfaceWater;
    }
    public void setSurfaceWater(String surfaceWater) {
        this.surfaceWater = surfaceWater;
    }
    public String getTerrain() {
        return terrain;
    }
    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }
    public long getId(){
        return planet_id;
    }
    public void setId(long id){
        this.planet_id = id;
    }
    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Mission getMission() {
        return mission;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\" :" + "\"" + planet_id + "\"," +
                "\"name\" :" + "\"" + name + "\"," +
                "\"diameterKM\" :" + "\"" + diameter + "\"" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return planet_id == planet.planet_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(planet_id);
    }
}