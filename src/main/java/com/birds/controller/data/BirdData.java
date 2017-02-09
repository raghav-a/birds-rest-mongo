package com.birds.controller.data;

import java.util.List;


@SpringDataClass
public class BirdData {

    private String name;
    private String family;
    private List<String> continents;
    private String added;
    private Boolean visibility;
    private String id;

    public BirdData(String name, String family, List<String> continents, String added, Boolean visibility) {
        this.name = name;
        this.continents = continents;
        this.added = added;
        this.family = family;
        this.visibility = visibility;
    }

    public BirdData() {
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

    public List<String> getContinents() {
        return continents;
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

    public String getId() {
        return id;
    }


    public String setId(String id) {
        return this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BirdData)) return false;

        BirdData birdData = (BirdData) o;

        if (name != null ? !name.equals(birdData.name) : birdData.name != null) return false;
        if (family != null ? !family.equals(birdData.family) : birdData.family != null) return false;
        if (continents != null ? !continents.equals(birdData.continents) : birdData.continents != null) return false;
        if (added != null ? !added.equals(birdData.added) : birdData.added != null) return false;
        if (visibility != null ? !visibility.equals(birdData.visibility) : birdData.visibility != null) return false;
        return true;

    }


}
