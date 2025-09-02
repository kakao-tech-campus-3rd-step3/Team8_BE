package com.kakaotechcampus.journey_planner.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<String> exceptionHandler(CustomRuntimeException e){
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
