package com.birds.model;

import com.birds.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.*;


public enum Continent {

    africa, north_america("north america"), south_america("south america"), australia, europe, antarctica, asia;

    private final String asString;

    Continent(String asString) {
        this.asString = asString;
    }

    Continent() {
        this.asString = this.name();
    }

    public static Continent getFor(String value){
        return Arrays.stream(values()).
            filter(continent -> continent.asString.equals(value))
            .findFirst()
            .orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST));
    }

    public static Set<Continent> convert(List<String> continents) {
        final HashSet<Continent> converted = new HashSet<>();
        if(continents==null) {
            return converted;
        }
        for (String continent : continents) {
            converted.add(getFor(continent));
        }
        return converted;
    }

    public static List<String> convert(Set<Continent> continents) {
        final List<String> converted = new ArrayList<>();
        for (Continent continent : continents) {
            converted.add(continent.asString);
        }
        return converted;
    }
}
