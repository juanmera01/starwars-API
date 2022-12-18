package com.juan.starwarsapi.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    @OneToMany(mappedBy = "people_id")
    private transient List<People> pilots = new ArrayList<>();

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
                    List<People> pilots, String starshipClass, String url) {
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
        return Integer.parseInt(crew);
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
        return Integer.parseInt(passengers);
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public List<People> getPilots() {
        return pilots;
    }

    public void setPilots(List<People> pilots) {
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
        StringBuilder sb = new StringBuilder();
        sb.append(Starship.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("mglt");
        sb.append('=');
        sb.append(((this.mglt == null)?"<null>":this.mglt));
        sb.append(',');
        sb.append("cargoCapacity");
        sb.append('=');
        sb.append(((this.cargoCapacity == null)?"<null>":this.cargoCapacity));
        sb.append(',');
        sb.append("consumables");
        sb.append('=');
        sb.append(((this.consumables == null)?"<null>":this.consumables));
        sb.append(',');
        sb.append("costInCredits");
        sb.append('=');
        sb.append(((this.costInCredits == null)?"<null>":this.costInCredits));
        sb.append(',');
        sb.append("created");
        sb.append('=');
        sb.append(((this.created == null)?"<null>":this.created));
        sb.append(',');
        sb.append("crew");
        sb.append('=');
        sb.append(this.crew);
        sb.append(',');
        sb.append("edited");
        sb.append('=');
        sb.append(((this.edited == null)?"<null>":this.edited));
        sb.append(',');
        sb.append("hyperdriveRating");
        sb.append('=');
        sb.append(((this.hyperdriveRating == null)?"<null>":this.hyperdriveRating));
        sb.append(',');
        sb.append("length");
        sb.append('=');
        sb.append(((this.length == null)?"<null>":this.length));
        sb.append(',');
        sb.append("manufacturer");
        sb.append('=');
        sb.append(((this.manufacturer == null)?"<null>":this.manufacturer));
        sb.append(',');
        sb.append("maxAtmospheringSpeed");
        sb.append('=');
        sb.append(((this.maxAtmospheringSpeed == null)?"<null>":this.maxAtmospheringSpeed));
        sb.append(',');
        sb.append("model");
        sb.append('=');
        sb.append(((this.model == null)?"<null>":this.model));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("passengers");
        sb.append('=');
        sb.append(this.passengers);
        sb.append(',');
        sb.append("pilots");
        sb.append('=');
        sb.append(((this.pilots == null)?"<null>":this.pilots));
        sb.append(',');
        sb.append("starshipClass");
        sb.append('=');
        sb.append(((this.starshipClass == null)?"<null>":this.starshipClass));
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}