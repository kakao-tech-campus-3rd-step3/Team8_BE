package com.kakaotechcampus.journey_planner.presentation.dto;

import java.time.LocalDateTime;

public record WaypointRequest(
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
