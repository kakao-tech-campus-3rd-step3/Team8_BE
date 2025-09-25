package com.kakaotechcampus.journey_planner.presentation.waypoint.dto.response;

import com.kakaotechcampus.journey_planner.domain.waypoint.LocationCategory;
import com.kakaotechcampus.journey_planner.domain.waypoint.LocationSubCategory;

import java.time.LocalDateTime;

public record WaypointResponse(
        Long id,

        String name,
        String description,

        String address,

        LocalDateTime startTime,
        LocalDateTime endTime,

        LocationCategory locationCategory,
        LocationSubCategory locationSubCategory,

        Float xPosition,
        Float yPosition
) {
}