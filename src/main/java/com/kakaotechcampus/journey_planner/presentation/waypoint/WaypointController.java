package com.kakaotechcampus.journey_planner.presentation.waypoint;

import com.kakaotechcampus.journey_planner.application.waypoint.WaypointService;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.domain.waypoint.WaypointMapper;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WaypointRequest;
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
        sendFullWaypoints(planId);
    }

    // 새 waypoint 생성 후 전체 리스트 전송
    @MessageMapping("/create")
    public void createWaypoint(@DestinationVariable Long planId, @Valid @Payload WaypointRequest request) {
        Waypoint waypoint = WaypointMapper.toEntity(request);
        waypointService.addWaypoint(planId, waypoint);

        sendFullWaypoints(planId);
    }

    // waypoint 수정 후 전체 리스트 전송
    @MessageMapping("/{waypointId}/update")
    public void updateWaypoint(
            @DestinationVariable Long planId,
            @DestinationVariable Long waypointId,
            @Valid WaypointRequest request) {

        waypointService.updateWaypoint(planId, waypointId, request);

        sendFullWaypoints(planId);
    }

     // waypoint 삭제 후 전체 리스트 전송
    @MessageMapping("/{waypointId}/delete")
    public void deleteWaypoint(
            @DestinationVariable Long planId,
            @DestinationVariable Long waypointId
    ) {
        waypointService.removeWaypoint(planId, waypointId);

        sendFullWaypoints(planId);
    }

    // plan의 전체 waypoint 리스트를 전송 (Broadcast)
    private void sendFullWaypoints(Long planId) {
        List<Waypoint> waypoints = waypointService.getWaypoints(planId);
        messagingTemplate.convertAndSend(
                "/topic/plans/" + planId + "/waypoints",
                Map.of(
                "type", "FULL_UPDATE",
                "waypoints", WaypointMapper.toResponseList(waypoints)
                )
        );
    }
}