package com.kakaotechcampus.journey_planner.presentation.route;

import com.kakaotechcampus.journey_planner.application.route.RouteService;
import com.kakaotechcampus.journey_planner.presentation.route.dto.request.RouteRequest;
import com.kakaotechcampus.journey_planner.presentation.route.dto.response.RouteResponse;
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
@MessageMapping("/plans/{planId}/routes")
public class RouteController {

    private final RouteService routeService;
    private final SimpMessagingTemplate messagingTemplate;

    // 클라이언트가 구독 후 초기 상태 요청 시 전체 Route 전송
    @MessageMapping("/init")
    public void initRoutes(@DestinationVariable Long planId) {
        List<RouteResponse> routeResponses = routeService.getRoutes(planId);

        sendResponse(planId, "ROUTE_INIT", "routes", routeResponses);
    }

    // 새 route 생성 후 단건 전송
    @MessageMapping("/create")
    public void create(@DestinationVariable Long planId, @Valid @Payload RouteRequest request) {
        RouteResponse response = routeService.create(planId, request);
        sendResponse(planId, "ROUTE_CREATE", "route", response);
    }

    // route 수정 후 단건 전송
    @MessageMapping("/{routeId}/update")
    public void update(
            @DestinationVariable Long planId,
            @DestinationVariable Long routeId,
            @Valid @Payload RouteRequest request
    ) {
        RouteResponse response = routeService.update(planId, routeId, request);
        sendResponse(planId, "ROUTE_UPDATE", "route", response);
    }

    // route 삭제 후 해당 Id 전송
    @MessageMapping("/{routeId}/delete")
    public void delete(@DestinationVariable Long planId, @DestinationVariable Long routeId) {
        routeService.delete(planId, routeId);
        sendResponse(planId, "ROUTE_DELETE", "routeId", routeId);
    }

    // 변경 사항을 클라이언트에 전송 (Broadcast)
    private void sendResponse(Long planId, String type, String payloadType, Object payload) {
        messagingTemplate.convertAndSend(
                "/topic/plans/" + planId + "/routes",
                Map.of(
                        "type", type,
                        payloadType, payload
                )
        );
    }
}