package com.kakaotechcampus.journey_planner.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BusinessException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getCode(), e.getMessage()));
    }
}
