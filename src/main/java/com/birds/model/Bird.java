package com.birds.model;

import com.birds.controller.data.BirdData;
import com.birds.exceptions.ApplicationException;
import com.birds.helpers.JsonBuilder;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;

import java.util.Set;

public class Bird {


    @Id
    private ObjectId id;
    private String name;
    private String family;
    private Set<Continent> continents;
    private ApplicationDate added;
    private Boolean visibility;

    public Bird(BirdData birdData) {
        this.name = validateName(birdData.getName());
        this.family = birdData.getFamily();
        this.continents = Continent.convert(birdData.getContinents());
        this.visibility = birdData.getVisibility();
        this.added = new ApplicationDate(birdData.added());
    }

    private String validateName(String name) {
        if(name==null)
            throw  new ApplicationException(HttpStatus.BAD_REQUEST);
        return name;
    }

    public Bird() {
    }


    public Boolean isVisibile() {
        return visibility != null && visibility;
    }

    public ObjectId id() {
        return id;
    }

    public String idAsHex() {
        return id != null ? id.toHexString() : "";
    }


    public String toJson(JsonBuilder.JsonBuilderFactory jsonBuilderFactory) {
        final JsonBuilder jsonBuilder = jsonBuilderFactory.newBuilder();
        jsonBuilder.addProperty("id", id.toHexString());
        jsonBuilder.addProperty("name", name);
        jsonBuilder.addProperty("family", family);
        jsonBuilder.addProperty("continents", Continent.convert(continents));
        jsonBuilder.addProperty("added", added.toString());
        jsonBuilder.addProperty("visibility", visibility);
        return jsonBuilder.build();

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
