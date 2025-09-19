package com.kakaotechcampus.journey_planner.presentation.waypoint;

import com.kakaotechcampus.journey_planner.application.waypoint.WaypointService;
import com.kakaotechcampus.journey_planner.presentation.utils.MessagingUtil;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WaypointRequest;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.response.WaypointResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@MessageMapping("/plans/{planId}/waypoints")
public class WaypointController {

    private final WaypointService waypointService;
    private final MessagingUtil messagingUtil;
    static final String destination = "waypoints";

    //클라이언트가 구독 후 초기 상태 요청 시 전체 waypoint 전송
    @MessageMapping("/init")
    public void initWaypoints(@DestinationVariable Long planId) {

        List<WaypointResponse> waypointResponses = waypointService.getWaypoints(planId);

        messagingUtil.sendResponse(planId, destination, "WAYPOINT_INIT", "waypoints", waypointResponses);
    }

    // 새 waypoint 생성 후 단건 전송
    @MessageMapping("/create")
    public void createWaypoint(@DestinationVariable Long planId, @Valid @Payload WaypointRequest request) {

        WaypointResponse response = waypointService.addWaypoint(planId, request);

        messagingUtil.sendResponse(planId, destination, "WAYPOINT_CREATE",  "waypoint", response);
    }

    // waypoint 수정 후 단건 전송
    @MessageMapping("/{waypointId}/update")
    public void updateWaypoint(
            @DestinationVariable Long planId,
            @DestinationVariable Long waypointId,
            @Valid @Payload WaypointRequest request) {

        WaypointResponse response = waypointService.updateWaypoint(planId, waypointId, request);

        messagingUtil.sendResponse(planId, destination, "WAYPOINT_UPDATE", "waypoint", response);
    }

    // waypoint 삭제 후 해당 Id 전송
    @MessageMapping("/{waypointId}/delete")
    public void deleteWaypoint(
            @DestinationVariable Long planId,
            @DestinationVariable Long waypointId) {

        waypointService.removeWaypoint(planId, waypointId);

        messagingUtil.sendResponse(planId, destination, "WAYPOINT_DELETE", "waypointId", waypointId);
    }
}