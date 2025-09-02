package com.kakaotechcampus.journey_planner.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record ErrorResponse(String code, String message) {}
