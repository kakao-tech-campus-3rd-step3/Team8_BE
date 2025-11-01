package com.kakaotechcampus.journey_planner.presentation.memo.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemoRequest(

    String title,

    String content,

    @NotNull(message = "x좌표는 필수 입력값입니다.")
    Float xPosition,

    @NotNull(message = "y좌표는 필수 입력값입니다.")
    Float yPosition,

    Long waypointId,

    Long routeId
) {}
