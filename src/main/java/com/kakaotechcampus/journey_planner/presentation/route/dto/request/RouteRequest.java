package com.kakaotechcampus.journey_planner.presentation.route.dto.request;


import com.kakaotechcampus.journey_planner.domain.route.VehicleCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RouteRequest(

        @NotNull(message = "출발 웨이포인트 ID는 필수입니다.")
        Long fromWaypointId,

        @NotNull(message = "도착 웨이포인트 ID는 필수입니다.")
        Long toWaypointId,

        @NotBlank(message = "제목은 필수입니다.")
        String title,

        String description,

        @NotNull(message = "duration은 필수입니다.")
        @Positive(message = "duration은 0보다 커야 합니다.")
        Float duration,

        @NotNull(message = "vehicleCategory는 필수입니다.")
        VehicleCategory vehicleCategory
) {
}
