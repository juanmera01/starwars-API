package com.juan.starwarsapi.entities;

import java.util.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;

@Entity
public class Starship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long starship_id;

    @SerializedName("MGLT")
    @Expose
    private String mglt;
    @SerializedName("cargo_capacity")
    @Expose
    private String cargoCapacity;
    @SerializedName("consumables")
    @Expose
    private String consumables;
    @SerializedName("cost_in_credits")
    @Expose
    private String costInCredits;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("crew")
    @Expose
    private String crew;
    @SerializedName("edited")
    @Expose
    private String edited;
    @SerializedName("hyperdrive_rating")
    @Expose
    private String hyperdriveRating;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("max_atmosphering_speed")
    @Expose
    private String maxAtmospheringSpeed;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("passengers")
    @Expose
    private String passengers;

    @JsonIgnore
    @ManyToMany
    private Set<People> pilots = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "id")
    private Mission mission;

    @SerializedName("starship_class")
    @Expose
    private String starshipClass;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     *
     */
    public Starship() {
    }

    public Starship(String mglt, String cargoCapacity, String consumables,
                    String costInCredits, String created, String crew, String edited,
                    String hyperdriveRating, String length, String manufacturer,
                    String maxAtmospheringSpeed, String model, String name, String passengers,
                    Set<People> pilots, String starshipClass, String url) {
        this.mglt = mglt;
        this.cargoCapacity = cargoCapacity;
        this.consumables = consumables;
        this.costInCredits = costInCredits;
        this.created = created;
        this.crew = crew;
        this.edited = edited;
        this.hyperdriveRating = hyperdriveRating;
        this.length = length;
        this.manufacturer = manufacturer;
        this.maxAtmospheringSpeed = maxAtmospheringSpeed;
        this.model = model;
        this.name = name;
        this.passengers = passengers;
        this.pilots = pilots;
        this.starshipClass = starshipClass;
        this.url = url;
    }

    public String getMglt() {
        return mglt;
    }

    public void setMglt(String mglt) {
        this.mglt = mglt;
    }

    public String getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(String cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    public String getCostInCredits() {
        return costInCredits;
    }

    public void setCostInCredits(String costInCredits) {
        this.costInCredits = costInCredits;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getCrew() {
        return parseNumber(crew);
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getHyperdriveRating() {
        return hyperdriveRating;
    }

    public void setHyperdriveRating(String hyperdriveRating) {
        this.hyperdriveRating = hyperdriveRating;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMaxAtmospheringSpeed() {
        return maxAtmospheringSpeed;
    }

    public void setMaxAtmospheringSpeed(String maxAtmospheringSpeed) {
        this.maxAtmospheringSpeed = maxAtmospheringSpeed;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassengers() {
        return parseNumber(passengers);
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public Set<People> getPilots() {
        return pilots;
    }

    public void setPilots(Set<People> pilots) {
        this.pilots = pilots;
    }

    public String getStarshipClass() {
        return starshipClass;
    }

    public void setStarshipClass(String starshipClass) {
        this.starshipClass = starshipClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId(){
        return starship_id;
    }

    public void setId(long id){
        this.starship_id = id;
    }

    public void setStarship_id(long starship_id) {
        this.starship_id = starship_id;
    }

    public void setMissions(Mission mission) {
        this.mission = mission;
    }

    public long getStarship_id() {
        return starship_id;
    }

    public Mission getMissions() {
        return mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Starship starShip = (Starship) o;
        return starship_id == starShip.starship_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(starship_id);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\" :" + "\"" + starship_id + "\"," +
                "\"name\" :" + "\"" + name + "\"," +
                "\"crew\" :" + "\"" + crew + "\"," +
                "\"passengers\" :" + "\"" + passengers + "\"," +
                "}";
    }

    private int parseNumber(String number){
        if(number.equals("n/a"))
            return -1;
        if(number.equals("unknow"))
            return -1;
        String[] nubmerParts = number.split("-");
        if(nubmerParts.length > 1)
            return Integer.parseInt(nubmerParts[1]);
        nubmerParts = number.split(",");
        if(nubmerParts.length > 1)
            return Integer.parseInt(nubmerParts[0]+nubmerParts[1]);
        return Integer.parseInt(number);
    }

}
