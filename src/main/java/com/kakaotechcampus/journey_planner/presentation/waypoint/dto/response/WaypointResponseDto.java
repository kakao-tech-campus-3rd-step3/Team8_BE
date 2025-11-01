package com.kakaotechcampus.journey_planner.presentation.waypoint.dto.response;

import com.kakaotechcampus.journey_planner.domain.waypoint.LocationCategory;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;

import java.time.LocalDateTime;

public record WaypointResponseDto(
        Long id,
        String uuid,
        String name,
        String description,
        String address,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocationCategory locationCategory,
        Float xPosition,
        Float yPosition
) {
    public static WaypointResponseDto of(Waypoint waypoint) {
        return new WaypointResponseDto(
                waypoint.getId(),
                waypoint.getUuid(),
                waypoint.getName(),
                waypoint.getDescription(),
                waypoint.getAddress(),
                waypoint.getStartTime(),
                waypoint.getEndTime(),
                waypoint.getLocationCategory(),
                waypoint.getXPosition(),
                waypoint.getYPosition()
        );
    }
}