package com.birds.model;

import com.birds.controller.BirdsController;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Bird {


    @Id
    private BigInteger id;

    public Bird(BirdData birdData) {
        this.type = birdData.getType();
        this.name = birdData.getName();
    }

    @Override
    public String toString() {
        return "Bird{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    private  String type;
    private  String name;

    public Bird(BigInteger id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Bird() {
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


}
