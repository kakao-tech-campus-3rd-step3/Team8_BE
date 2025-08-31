package com.kakaotechcampus.journey_planner.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    NO_FILE(HttpStatus.NOT_FOUND, "NO_FILE","파일 경로를 찾을 수 없습니다."),
    PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "PLAN_NOT_FOUND", "해당 계획을 찾을 수 없습니다.");

    private HttpStatus status;
    private final String code;
    private String message;
}
