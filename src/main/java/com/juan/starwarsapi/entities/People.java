package com.juan.starwarsapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long people_id;

    @SerializedName("birth_year")
    @Expose
    private String birthYear;
    @SerializedName("eye_color")
    @Expose
    private String eyeColor;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("hair_color")
    @Expose
    private String hairColor;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("homeworld")
    @Expose
    private String homeworld;
    @SerializedName("mass")
    @Expose
    private String mass;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("skin_color")
    @Expose
    private String skinColor;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("edited")
    @Expose
    private String edited;

    @JsonIgnore
    @ManyToMany
    private Set<Starship> starships = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @SerializedName("url")
    @Expose
    private String url;

    public People(){}

    public People(String birthYear, String eyeColor,
                  String gender, String hairColor, String height, String homeworld,
                  String mass, String name, String skinColor, String created,
                  String edited, Set<Starship> starships,
                  String url) {
        this.birthYear = birthYear;
        this.eyeColor = eyeColor;
        this.gender = gender;
        this.hairColor = hairColor;
        this.height = height;
        this.homeworld = homeworld;
        this.mass = mass;
        this.name = name;
        this.skinColor = skinColor;
        this.created = created;
        this.edited = edited;
        this.starships = starships;
        this.url = url;
    }

    public String getBirthYear() {
        return birthYear;
    }
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }
    public String getEyeColor() {
        return eyeColor;
    }
    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getHairColor() {
        return hairColor;
    }
    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getHomeworld() {
        return homeworld;
    }
    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }
    public String getMass() {
        return mass;
    }
    public void setMass(String mass) {
        this.mass = mass;
    }
    public String getName() {
        return name;
    }
    public long getId() {
        return people_id;
    }
    public Mission getMission() {
        return mission;
    }
    public Set<Starship> getStarships() {
        return starships;
    }
    public String getUrl() {
        return url;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSkinColor() {
        return skinColor;
    }
    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public String getEdited() {
        return edited;
    }
    public void setEdited(String edited) {
        this.edited = edited;
    }
    public void setStarships(Set<Starship> starships) {
        this.starships = starships;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return getId() == people.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString(){
        return "{" +
                "\"id\" : " + "\"" + people_id +"\"," +
                "\"name\" : " + "\"" + name +"\"" +
                "}";
    }
}
