package com.kakaotechcampus.journey_planner.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NO_FILE(HttpStatus.NOT_FOUND, "NO_FILE","파일 경로를 찾을 수 없습니다."),
    PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "PLAN_NOT_FOUND", "해당 계획을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "해당 멤버를 찾을 수 없습니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "LOGIN_FAILED", "로그인에 실패하였습니다."),
    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "ALREADY_REGISTERED", "이미 존재하는 멤버입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "INVALID_TOKEN", "잘못된 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN_EXPIRED", "만료된 토큰입니다.");


    private HttpStatus status;
    private final String code;
    private String message;
}
