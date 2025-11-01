package com.kakaotechcampus.journey_planner.presentation.route;

import com.kakaotechcampus.journey_planner.application.route.RouteService;
import com.kakaotechcampus.journey_planner.presentation.route.dto.request.RouteRequest;
import com.kakaotechcampus.journey_planner.presentation.route.dto.response.RouteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.kakaotechcampus.journey_planner.domain.message.MessageType.ROUTE;

@Slf4jvelop
@Controller
@RequiredArgsConstructor
@MessageMapping("/plans/{planId}/routes")
public class RouteController {

    private final RouteService routeService;
    private static final String DESTINATION = "routes";


    @MessageMapping("/init")
    public void initRoutes(@DestinationVariable Long planId) {
        List<RouteResponse> routeResponses = routeService.getRoutes(planId);
    }


    @MessageMapping("/create")
    public void createRoute(
            @DestinationVariable Long planId,
            @Valid @Payload RouteRequest request,
            @Header("simpSessionId") String sessionId
    ) {
        log.info("🟢 [CREATE ROUTE] sessionId={}", sessionId);
        RouteResponse response = routeService.createRoute(planId, request);
    }

    // 수정 (자기 세션 제외 브로드캐스트)
    @MessageMapping("/{routeId}/update")
    public void updateRoute(
            @DestinationVariable Long planId,
            @DestinationVariable Long routeId,
            @Valid @Payload RouteRequest request,
            @Header("simpSessionId") String sessionId
    ) {
        RouteResponse response = routeService.updateRoute(planId, routeId, request);
        messageService.sendUpdateMessage(ROUTE, planId, DESTINATION, response, sessionId);
    }


    @MessageMapping("/{routeId}/delete")
    public void deleteRoute(
            @DestinationVariable Long planId,
            @DestinationVariable Long routeId
    ) {
        routeService.deleteRoute(planId, routeId);
    }
}
