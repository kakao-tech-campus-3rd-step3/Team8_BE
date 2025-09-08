package com.kakaotechcampus.journey_planner.presentation.dto.waypoint;

import com.kakaotechcampus.journey_planner.domain.waypoint.LocationCategory;

import java.time.LocalDateTime;

public record WaypointResponse(
        Long id,

        String name,
        String description,

        String address,

        LocalDateTime startTime,
        LocalDateTime endTime,

        LocationCategory locationCategory,

        Float xPosition,
        Float yPosition
) {}