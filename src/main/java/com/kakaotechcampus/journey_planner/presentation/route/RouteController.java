package com.kakaotechcampus.journey_planner.presentation.route;

import com.kakaotechcampus.journey_planner.application.route.RouteService;
import com.kakaotechcampus.journey_planner.presentation.route.dto.request.RouteRequest;
import com.kakaotechcampus.journey_planner.presentation.route.dto.response.RouteResponse;
import com.kakaotechcampus.journey_planner.presentation.utils.MessagingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@MessageMapping("/plans/{planId}/routes")
public class RouteController {

    private final RouteService routeService;
    private final MessagingUtil messagingUtil;
    static final String destination = "routes";

    // 클라이언트가 구독 후 초기 상태 요청 시 전체 Route 전송
    @MessageMapping("/init")
    public void initRoutes(@DestinationVariable Long planId) {
        List<RouteResponse> routeResponses = routeService.getRoutes(planId);

        messagingUtil.sendResponse(planId, destination, "ROUTE_INIT", "routes", routeResponses);
    }

    // 새 route 생성 후 단건 전송
    @MessageMapping("/create")
    public void createRoute(@DestinationVariable Long planId, @Valid @Payload RouteRequest request) {
        RouteResponse response = routeService.createRoute(planId, request);
        messagingUtil.sendResponse(planId, destination, "ROUTE_CREATE", "route", response);
    }

    // route 수정 후 단건 전송
    @MessageMapping("/{routeId}/update")
    public void updateRoute(
            @DestinationVariable Long planId,
            @DestinationVariable Long routeId,
            @Valid @Payload RouteRequest request
    ) {
        RouteResponse response = routeService.updateRoute(planId, routeId, request);
        messagingUtil.sendResponse(planId, destination, "ROUTE_UPDATE", "route", response);
    }

    // route 삭제 후 해당 Id 전송
    @MessageMapping("/{routeId}/delete")
    public void deleteRoute(@DestinationVariable Long planId, @DestinationVariable Long routeId) {
        routeService.deleteRoute(planId, routeId);
        messagingUtil.sendResponse(planId, destination, "ROUTE_DELETE", "routeId", routeId);
    }
}