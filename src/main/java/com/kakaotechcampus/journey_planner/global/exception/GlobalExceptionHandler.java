package com.kakaotechcampus.journey_planner.global.exception;

import com.kakaotechcampus.journey_planner.presentation.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handlePlanNotFound(PlanNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404, new ErrorBody("PLAN_NOT_FOUND", e.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, new ErrorBody("INVALID_INPUT", e.getMessage())));
    }



    record ErrorBody(String code, String message) {}
}
