package com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request;

import com.kakaotechcampus.journey_planner.domain.waypoint.LocationCategory;
import com.kakaotechcampus.journey_planner.domain.waypoint.LocationSubCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record WaypointRequest(
        @Size(max = 20, message = "웨이포인트명은 최대 20자까지 허용됩니다.")
        String name,

        String description,

        String address,

        LocalDateTime startTime,

        LocalDateTime endTime,

        LocationCategory locationCategory,

        LocationSubCategory locationSubCategory,

        @NotNull(message = "x좌표는 필수 입력값입니다.")
        Float xPosition,

        @NotNull(message = "y좌표는 필수 입력값입니다.")
        Float yPosition
) {
}