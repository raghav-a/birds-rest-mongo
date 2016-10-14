package com.birds.model;

public class BirdData {

    private String name;
    private String type;

    public BirdData() {
    }

    public BirdData(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


}
