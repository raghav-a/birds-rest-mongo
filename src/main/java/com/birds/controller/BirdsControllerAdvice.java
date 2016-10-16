package com.birds.controller;

import com.birds.exceptions.ApplicationException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = BirdsRegistryController.class)
public class BirdsControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, ApplicationException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(null);
    }

}


