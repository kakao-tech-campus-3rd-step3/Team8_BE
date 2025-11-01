package com.kakaotechcampus.journey_planner.domain.waypoint;

import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WaypointRequestDto;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.response.WaypointResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class WaypointMapper {

    public static Waypoint toEntity(WaypointRequestDto request) {
        return new Waypoint(
                request.name(),
                request.description(),
                request.address(),
                request.startTime(),
                request.endTime(),
                request.locationCategory(),
                request.xPosition(),
                request.yPosition()
        );
    }

    public static List<WaypointResponseDto> toResponseList(List<Waypoint> waypoints) {
        return waypoints.stream()
                .map(WaypointResponseDto::of)
                .collect(Collectors.toList());
    }
}
