package com.birds.payloads;

import java.math.BigInteger;

public class BirdCreationResponse extends GenericResponse {
    private final BigInteger birdId;

    public BirdCreationResponse(String message, BigInteger id) {
        super(message);
        this.birdId= id;
    }

    public BigInteger getBirdId() {
        return birdId;
    }
}
