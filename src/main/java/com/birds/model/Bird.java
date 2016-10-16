package com.birds.model;

import com.birds.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Set;

public class Bird {

    private static Gson gson = new Gson();

    @Id
    private ObjectId id;
    private String name;
    private String family;
    private Set<String> continents;
    private String added;
    private Boolean visibility;

    public Bird(BirdData birdData) {
        this.name = birdData.getName();
        this.family = birdData.getFamily();
        this.continents = birdData.getContinentsAsSet();
        this.visibility = birdData.getVisibility();
        this.added = birdData.added();
    }

    public Bird() {
    }

    public static BirdBuilder builder() {
        return new BirdBuilder();
    }

    public static final class BirdBuilder {
        private String name;
        private Boolean visibility;
        private Set<String> continents;
        private String family;
        private String added;

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

        public BirdBuilder added(String added) {
            this.added = added;
            return this;
        }

        public Bird build() {
            Bird bird = new Bird();
            bird.family = this.family;
            bird.continents = this.continents;
            bird.name = this.name;
            bird.visibility = this.visibility;
            bird.added = this.added;
            return bird;
        }
    }

    public String getAdded() {
        return added;
    }

    public Set<String> getContinents() {
        return continents;
    }

    public Boolean getVisibility() {
        return visibility != null && visibility;
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
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + family.hashCode();
        result = 31 * result + continents.hashCode();
        result = 31 * result + (added != null ? added.hashCode() : 0);
        result = 31 * result + (visibility != null ? visibility.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bird bird = (Bird) o;

        if (!name.equals(bird.name)) return false;
        if (!family.equals(bird.family)) return false;
        if (!continents.equals(bird.continents)) return false;
        if (added != null ? !added.equals(bird.added) : bird.added != null) return false;
        return !(visibility != null ? !visibility.equals(bird.visibility) : bird.visibility != null);

    }

    @Override
    public String toString() {
        return "Bird{" +
            "addedOn=" + added +
            ", id=" + id.toHexString() +
            ", name='" + name + '\'' +
            ", family='" + family + '\'' +
            ", continents=" + continents +
            ", visibility=" + visibility +
            '}';
    }
}
