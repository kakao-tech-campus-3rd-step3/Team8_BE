package com.kakaotechcampus.journey_planner.presentation.waypoint;

import com.kakaotechcampus.journey_planner.application.message.MessageService;
import com.kakaotechcampus.journey_planner.application.waypoint.WaypointService;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WaypointRequest;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.response.WaypointResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.kakaotechcampus.journey_planner.domain.message.MessageType.WAYPOINT;

@Slf4j
@Controller
@RequiredArgsConstructor
@MessageMapping("/plans/{planId}/waypoints")
public class WaypointController {

    private final WaypointService waypointService;
    private final MessageService messageService;
    private static final String DESTINATION = "waypoints";

    @MessageMapping("/init")
    public void initWaypoints(@DestinationVariable Long planId) {
        List<WaypointResponse> waypointResponses = waypointService.getWaypoints(planId);
        messageService.sendInitMessage(WAYPOINT, planId, DESTINATION, waypointResponses);
    }

    @MessageMapping("/create")
    public void createWaypoint(
            @DestinationVariable Long planId,
            @Valid @Payload WaypointRequest request,
            @Header("simpSessionId") String sessionId
    ) {
        WaypointResponse response = waypointService.createWaypoint(planId, request);
        messageService.sendCreateMessage(WAYPOINT, planId, DESTINATION, response);
    }

    @MessageMapping("/{waypointId}/update")
    public void updateWaypoint(
            @DestinationVariable Long planId,
            @DestinationVariable Long waypointId,
            @Valid @Payload WaypointRequest request,
            @Header("simpSessionId") String sessionId
    ) {

        WaypointResponse response = waypointService.updateWaypoint(planId, waypointId, request);

        // 자기 세션 제외 브로드캐스트
        messageService.sendUpdateMessage(WAYPOINT, planId, DESTINATION, response, sessionId);
    }

    @MessageMapping("/{waypointId}/delete")
    public void deleteWaypoint(
            @DestinationVariable Long planId,
            @DestinationVariable Long waypointId
    ) {
        waypointService.deleteWaypoint(planId, waypointId);
        messageService.sendDeleteMessage(WAYPOINT, planId, DESTINATION, waypointId);
    }
}
