package com.kakaotechcampus.journey_planner.presentation.waypoint;

import com.kakaotechcampus.journey_planner.application.waypoint.WaypointService;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WaypointRequestDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@MessageMapping("/plans/{planId}/waypoints")
public class WaypointController {
    private final WaypointService waypointService;

    @MessageMapping("/create")
    public void initWaypoint(@DestinationVariable Long planId, @Payload WaypointRequestDto request) {
        waypointService.createWaypoint(planId, request);
    }

    @MessageMapping("/edit")
    public void editWaypoint(@DestinationVariable Long planId, @Payload WaypointRequestDto request) {
    }

    @MessageMapping("/delete")
    public void deleteWaypoint(@DestinationVariable Long planId, @Payload WaypointRequestDto request) {}
}