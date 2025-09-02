package com.kakaotechcampus.journey_planner.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // * On FILE Exception
    NO_FILE(HttpStatus.NOT_FOUND, "파일 경로를 찾을 수 없습니다.");

    private HttpStatus status;
    private String message;
}
