package com.kakaotechcampus.journey_planner.domain.waypoint;

import com.kakaotechcampus.journey_planner.presentation.dto.waypoint.WaypointRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.waypoint.WaypointResponse;

import java.util.List;
import java.util.stream.Collectors;

public class WaypointMapper {

    public static Waypoint toEntity(WaypointRequest request) {
        return new Waypoint(
                request.name(),
                new Location(
                        request.location().address(),
                        request.location().latitude(),
                        request.location().longitude()
                ),
                request.arriveTime(),
                request.locationType(),
                request.memo()
        );
    }

    public static WaypointResponse toResponse(Waypoint waypoint) {
        return new WaypointResponse(
                waypoint.getId(),
                waypoint.getName(),
                new WaypointResponse.LocationDto(
                        waypoint.getLocation().getAddress(),
                        waypoint.getLocation().getLatitude(),
                        waypoint.getLocation().getLongitude()
                ),
                waypoint.getArriveTime(),
                waypoint.getLocationType(),
                waypoint.getMemo()
        );
    }

    public static List<WaypointResponse> toResponseList(List<Waypoint> waypoints) {
        return waypoints.stream()
                .map(WaypointMapper::toResponse)
                .collect(Collectors.toList());
    }
}
