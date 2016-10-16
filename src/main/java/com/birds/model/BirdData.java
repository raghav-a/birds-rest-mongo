package com.birds.model;

import com.birds.DateUtil;
import com.birds.exceptions.InvalidBirdDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.birds.exceptions.InvalidBirdDataException.INVALID_INPUT;
import static com.birds.exceptions.InvalidBirdDataException.REQUIRED_DATA_MISSING;

public class BirdData {

    private String name;
    private String family;
    private List<String> continents;
    private Boolean visibility;
    private Set<String> continentsAsSet;
    private String added;

    public BirdData() {
    }

    @Override
    public String toString() {
        return "BirdData{" +
            "added='" + added + '\'' +
            ", name='" + name + '\'' +
            ", family='" + family + '\'' +
            ", continents=" + continents +
            ", visibility=" + visibility +
            '}';
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void validate() throws InvalidBirdDataException {
        if (family == null || name == null || continents.size() < 1 || continents.size() > 7)
            throw new InvalidBirdDataException(REQUIRED_DATA_MISSING);
        continentsAsSet = new HashSet<>(continents);
        if (continentsAsSet.size() != continents.size())
            throw new InvalidBirdDataException(INVALID_INPUT);
        if(continentsAsSet.stream().anyMatch(continent -> !DateUtil.continents.contains(continent))){
            throw new InvalidBirdDataException(INVALID_INPUT);
        }
        if (added == null) {
            added = DateUtil.currentDate();
        } else {
            try {
                DateUtil.validate(added);
            } catch (IllegalStateException e) {
                throw new InvalidBirdDataException(INVALID_INPUT);
            }
        }

    }

    public List<String> getContinents() {
        return continents;
    }

    public Set<String> getContinentsAsSet() {
        return continentsAsSet;
    }


    public void setContinents(List<String> continents) {
        this.continents = continents;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public String added() {
        return added;
    }


}
