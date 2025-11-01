package com.kakaotechcampus.journey_planner.infra.message;

import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Slf4j
@Getter
@RequiredArgsConstructor
public class RedisException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private String code;

    public RedisException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }

    public RedisException(ErrorCode errorCode, Object... cause) {
        this.code = errorCode.getCode();
        this.message = String.format(errorCode.getMessage(), cause);
        this.status = errorCode.getStatus();
    }
}
