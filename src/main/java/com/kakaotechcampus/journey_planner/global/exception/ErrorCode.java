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

    // PDF
    NO_FILE(HttpStatus.NOT_FOUND, "NO_FILE","파일 경로를 찾을 수 없습니다."),

    // Waypoint
    WAYPOINT_NOT_FOUND(HttpStatus.NOT_FOUND, "WAYPOINT_NOT_FOUND", "해당 웨이포인트를 찾을 수 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "해당 멤버를 찾을 수 없습니다."),
    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "ALREADY_REGISTERED", "이미 존재하는 멤버입니다."),

    // Auth
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "LOGIN_FAILED", "로그인에 실패하였습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "INVALID_TOKEN", "잘못된 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN_EXPIRED", "만료된 토큰입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "입력 형식이 잘못되었습니다.");

    // * PDF
    CANNOT_CREATE(HttpStatus.INTERNAL_SERVER_ERROR, "CANNOT_CREATE_PDF", "PDF 를 생성할 수 없습니다.")
    ;
    
    private final HttpStatus status;
    private final String code;
    private final String message;
}
