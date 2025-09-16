package com.kakaotechcampus.journey_planner.presentation.waypoint;

import com.kakaotechcampus.journey_planner.application.waypoint.WaypointService;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WaypointRequest;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.response.WaypointResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@MessageMapping("/plans/{planId}/waypoints")
public class WaypointController {

    private final WaypointService waypointService;
    private final SimpMessagingTemplate messagingTemplate;

    //클라이언트가 구독 후 초기 상태 요청 시 전체 waypoint 전송
    @MessageMapping("/init")
    public void initWaypoints(@DestinationVariable Long planId) {

        List<WaypointResponse> waypointResponses = waypointService.getWaypoints(planId);

        sendResponse(planId, "WAYPOINT_INIT", "waypoints", waypointResponses);
    }

    // 새 waypoint 생성 후 단건 전송
    @MessageMapping("/create")
    public void createWaypoint(@DestinationVariable Long planId, @Valid @Payload WaypointRequest request) {

        WaypointResponse response = waypointService.addWaypoint(planId, request);

        sendResponse(planId, "WAYPOINT_CREATE",  "waypoint", response);
    }

    // waypoint 수정 후 단건 전송
    @MessageMapping("/{waypointId}/update")
    public void updateWaypoint(
            @DestinationVariable Long planId,
            @DestinationVariable Long waypointId,
            @Valid WaypointRequest request) {

        WaypointResponse response = waypointService.updateWaypoint(planId, waypointId, request);

        sendResponse(planId, "WAYPOINT_UPDATE", "waypoint", response);
    }

     // waypoint 삭제 후 해당 Id 전송
    @MessageMapping("/{waypointId}/delete")
    public void deleteWaypoint(
            @DestinationVariable Long planId,
            @DestinationVariable Long waypointId) {

        waypointService.removeWaypoint(planId, waypointId);

        sendResponse(planId, "WAYPOINT_DELETE", "waypointId", waypointId);
    }

    // 변경 사항을 클라이언트에 전송 (Broadcast)
    private void sendResponse(Long planId, String type, String payloadType, Object payload) {

        messagingTemplate.convertAndSend(
                "/topic/plans/" + planId + "/waypoints",
                Map.of(
                        "type", type,
                        payloadType, payload
                )
        );
    }
}