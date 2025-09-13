package com.kakaotechcampus.journey_planner.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // * GLOBAL

    // * PLAN
    NO_FILE(HttpStatus.NOT_FOUND, "NO_FILE","파일 경로를 찾을 수 없습니다."),
    PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "PLAN_NOT_FOUND", "해당 계획을 찾을 수 없습니다."),

    // * Resource
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "NO_RESOURCE", "리소스를 찾을 수 없습니다."),
    FONT_NOT_FOUND(HttpStatus.NOT_FOUND, "NO_FONT_RESOURCE", "폰트를 찾을 수 없습니다."),


    // * PDF
    CANNOT_CREATE(HttpStatus.INTERNAL_SERVER_ERROR, "CANNOT_CREATE_PDF", "PDF 를 생성할 수 없습니다.")
    ;
    
    private final HttpStatus status;
    private final String code;
    private final String message;
}
