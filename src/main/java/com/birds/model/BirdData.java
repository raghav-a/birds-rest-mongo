package com.birds.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.birds.model.BirdData.InvalidBirdDataException.INVALID_INPUT;
import static com.birds.model.BirdData.InvalidBirdDataException.REQUIRED_DATA_MISSING;

public class BirdData {

    private String name;
    private String family;
    private List<String> continents;
    private Boolean visibility ;
    private Set<String> continentsAsSet;

    public void setContinents(List<String> continents) {
        this.continents = continents;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }


    public BirdData() {
    }

    public BirdData(String name, String family, Set<String> continents, Boolean visibility) {
        this.name = name;
        this.family = family;
        this.continentsAsSet = continents;
        this.visibility = visibility;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public void validate() throws InvalidBirdDataException {
        if (family == null || name == null || continents.size() < 1 )
            throw new InvalidBirdDataException(REQUIRED_DATA_MISSING);
        continentsAsSet = new HashSet<>(continents);
        if (continentsAsSet.size() != continents.size())
            throw new InvalidBirdDataException(INVALID_INPUT);

    }


    public Set<String> getContinents() {
        return continentsAsSet;
    }

    public Boolean getVisibility() {
        return visibility;
    }


    public static class InvalidBirdDataException extends Exception {
        public static String REQUIRED_DATA_MISSING= "REQUIRED_DATA_MISSING";
        public static String INVALID_INPUT= "INVALID_INPUT";

        public InvalidBirdDataException(String message) {
            super(message);
        }
    }
}
