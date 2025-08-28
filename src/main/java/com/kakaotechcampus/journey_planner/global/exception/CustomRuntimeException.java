package com.kakaotechcampus.journey_planner.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public CustomRuntimeException(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public CustomRuntimeException(ErrorCode errorCode, Object... cause){
        this.status = errorCode.getStatus();
        this.message = String.format(errorCode.getMessage(), cause);
    }
}
