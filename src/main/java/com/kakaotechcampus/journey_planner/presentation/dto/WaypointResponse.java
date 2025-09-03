package com.kakaotechcampus.journey_planner.presentation.dto;

import java.time.LocalDateTime;

public record WaypointResponse(
        Long id,
        String name,
        LocationDto location,
        LocalDateTime arriveTime,
        String locationType,
        String memo
) {
    public record LocationDto(
            String address,
            Double latitude,
            Double longitude
    ) {}
}