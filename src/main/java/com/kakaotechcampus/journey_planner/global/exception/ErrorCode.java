package com.kakaotechcampus.journey_planner.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // * GLOBAL


    // * PLAN
    PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "PLAN_NOT_FOUND", "해당 계획을 찾을 수 없습니다."),
    PLAN_ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "PLAN_ACCESS_DENIED", "해당 계획에 접근할 권한이 없습니다."),
    MEMBER_ALREADY_IN_PLAN(HttpStatus.BAD_REQUEST, "MEMBER_ALREADY_IN_PLAN", "해당 계획에 이미 참여 중입니다."),
    INVITATION_NOT_FOUND(HttpStatus.NOT_FOUND, "INVITATION_NOT_FOUND", "해당 초대장이 존재하지 않습니다."),
    INVITATION_ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "INVITATION_ACCESS_DENIED", "해당 초대장에 접근할 권한이 없습니다."),
    INVITATION_ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "INVITATION_ALREADY_PROCESSED", "이미 승인된 초대장 입니다."),
    // * Resource
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "NO_RESOURCE", "리소스를 찾을 수 없습니다."),
    FONT_NOT_FOUND(HttpStatus.NOT_FOUND, "NO_FONT_RESOURCE", "폰트를 찾을 수 없습니다."),

    // PDF
    NO_FILE(HttpStatus.NOT_FOUND, "NO_FILE","파일 경로를 찾을 수 없습니다."),

    // Waypoint
    WAYPOINT_NOT_FOUND(HttpStatus.NOT_FOUND, "WAYPOINT_NOT_FOUND", "해당 웨이포인트를 찾을 수 없습니다."),
    INVALID_LOCATION_CATEGORY(HttpStatus.BAD_REQUEST, "INVALID_LOCATION_CATEGORY", "잘못된 카테고리 입니다"),
    //Route
    ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "ROUTE_NOT_FOUND", "해당 경로를 찾을 수 없습니다."),

    // Memo
    MEMO_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMO_NOT_FOUND", "해당 메모를 찾을 수 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "해당 멤버를 찾을 수 없습니다."),
    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "ALREADY_REGISTERED", "이미 존재하는 멤버입니다."),

    // Traveler
    TRAVELER_NOT_FOUND(HttpStatus.NOT_FOUND, "TRAVELER_NOT_FOUND", "해당 여행자를 찾을 수 없습니다."),

    // Auth
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "LOGIN_FAILED", "로그인에 실패하였습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "INVALID_TOKEN", "잘못된 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN_EXPIRED", "만료된 토큰입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "입력 형식이 잘못되었습니다."),
    NO_TOKEN(HttpStatus.UNAUTHORIZED, "NO_TOKEN", "토큰을 찾을 수 없습니다."),

    // * PDF
    CANNOT_CREATE(HttpStatus.INTERNAL_SERVER_ERROR, "CANNOT_CREATE_PDF", "PDF 를 생성할 수 없습니다.")
    ;
    
    private final HttpStatus status;
    private final String code;
    private final String message;
}
