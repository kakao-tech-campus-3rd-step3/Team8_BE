package com.kakaotechcampus.journey_planner.presentation.dto.waypoint;

import com.kakaotechcampus.journey_planner.domain.waypoint.LocationCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record WaypointRequest(
        @NotBlank(message = "웨이포인트명은 필수 입력값입니다.")
        @Size(max = 20, message = "웨이포인트명은 최대 20자까지 허용됩니다.")
        String name,

        String description,

        @NotBlank(message = "주소는 필수 입력값입니다.")
        String address,

        @NotNull(message = "시작 시각은 필수 입력값입니다.")
        LocalDateTime startTime,

        @NotNull(message = "종료 시각은 필수 입력값입니다.")
        LocalDateTime endTime,

        @NotNull(message = "위치 카테고리는 필수 입력값입니다.")
        LocationCategory locationCategory,

        @NotNull(message = "x좌표는 필수 입력값입니다.")
        Float xPosition,

        @NotNull(message = "y좌표는 필수 입력값입니다.")
        Float yPosition
) {}