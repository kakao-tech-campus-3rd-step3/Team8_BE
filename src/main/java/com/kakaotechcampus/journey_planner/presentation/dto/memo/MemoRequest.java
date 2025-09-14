package com.kakaotechcampus.journey_planner.presentation.dto.memo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemoRequest(

    @NotBlank(message = "제목은 필수 입력값입니다.")
    String title,

    @NotBlank(message = "내용은 필수 입력값입니다.")
    String content,

    @NotNull(message = "x좌표는 필수 입력값입니다.")
    Float xPosition,

    @NotNull(message = "y좌표는 필수 입력값입니다.")
    Float yPosition
) {}
