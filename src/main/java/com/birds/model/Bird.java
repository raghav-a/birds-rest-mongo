package com.birds.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Set;

public class Bird {

    private static Gson gson = new Gson();

    @Id
    private ObjectId id;
    private String name;
    private String family;
    private Set<String> continents;
    private String addedOn;

    public String getAddedOn() {
        return addedOn;
    }

    public Set<String> getContinents() {
        return continents;
    }

    public Boolean getVisibility() {
        return visibility != null && visibility;
    }

    private Boolean visibility;

    @Override
    public String toString() {
        return "Bird{" +
            "addedOn=" + addedOn +
            ", id=" + id.toHexString() +
            ", name='" + name + '\'' +
            ", family='" + family + '\'' +
            ", continents=" + continents +
            ", visibility=" + visibility +
            '}';
    }

    public Bird(BirdData birdData, String addedOn) {
        this.name = birdData.getName();
        this.family = birdData.getFamily();
        this.continents = birdData.getContinents();
        this.visibility = birdData.getVisibility();
        this.addedOn = addedOn;
    }

    public Bird() {
    }


    public ObjectId getId() {
        return id;
    }

    public String getIdAsHex() {
        return id!=null ? id.toHexString() : "";
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String toJson() {
        final JsonObject jsonElement = (JsonObject) gson.toJsonTree(this);
        jsonElement.addProperty("id", id.toHexString());
        return gson.toJson(jsonElement);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bird bird = (Bird) o;

        if (!name.equals(bird.name)) return false;
        if (!family.equals(bird.family)) return false;
        if (!continents.equals(bird.continents)) return false;
        if (addedOn != null ? !addedOn.equals(bird.addedOn) : bird.addedOn != null) return false;
        return !(visibility != null ? !visibility.equals(bird.visibility) : bird.visibility != null);

    }


    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + family.hashCode();
        result = 31 * result + continents.hashCode();
        result = 31 * result + (addedOn != null ? addedOn.hashCode() : 0);
        result = 31 * result + (visibility != null ? visibility.hashCode() : 0);
        return result;
    }

    public static BirdBuilder builder() {
        return new BirdBuilder();
    }

    public static final class BirdBuilder {
        private String name;
        private Boolean visibility;
        private Set<String> continents;
        private String family;
        private String addedOn;

        private BirdBuilder() {
        }


        public BirdBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BirdBuilder visibility(Boolean visibility) {
            this.visibility = visibility;
            return this;
        }

        public BirdBuilder continents(Set<String> continents) {
            this.continents = continents;
            return this;
        }

        public BirdBuilder family(String family) {
            this.family = family;
            return this;
        }

        public BirdBuilder addedOn(String addedOn) {
            this.addedOn = addedOn;
            return this;
        }

        public Bird build() {
            Bird bird = new Bird();
            bird.family = this.family;
            bird.continents = this.continents;
            bird.name = this.name;
            bird.visibility = this.visibility;
            bird.addedOn = this.addedOn;
            return bird;
        }
    }
}
