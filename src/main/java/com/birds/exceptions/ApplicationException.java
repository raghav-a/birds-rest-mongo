package com.birds.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    private final HttpStatus httpStatus;

    public ApplicationException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
