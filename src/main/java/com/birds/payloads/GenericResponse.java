package com.birds.payloads;

public class GenericResponse {
    public String getResponse() {
        return response;
    }

    private String response;

    public GenericResponse(String response) {
        this.response = response;
    }
}
