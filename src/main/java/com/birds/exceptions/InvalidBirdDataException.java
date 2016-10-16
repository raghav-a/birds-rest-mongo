package com.birds.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidBirdDataException extends ApplicationException {
    public static String REQUIRED_DATA_MISSING= "REQUIRED_DATA_MISSING";
    public static String INVALID_INPUT= "INVALID_INPUT";
    private String message;


    public InvalidBirdDataException(String message) {
        super(HttpStatus.BAD_REQUEST);
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
