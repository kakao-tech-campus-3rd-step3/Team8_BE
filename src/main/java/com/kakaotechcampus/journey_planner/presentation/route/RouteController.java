package com.kakaotechcampus.journey_planner.presentation.route;

import com.kakaotechcampus.journey_planner.application.route.RouteService;
import com.kakaotechcampus.journey_planner.domain.route.Route;
import com.kakaotechcampus.journey_planner.presentation.dto.route.RouteRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.route.RouteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@MessageMapping("/plans/{planId}/routes") // 클라 → 서버: /app/plans/{planId}/routes/...
public class RouteController {

    private final RouteService routeService;
    private final SimpMessagingTemplate messagingTemplate;


    //클라이언트가 구독 후 초기 상태 요청 시 전체 Route 전송
    @MessageMapping("/init")
    public void initRoute(@DestinationVariable Long planId) {
        sendFullRoutes(planId);
    }

    @MessageMapping("/create")
    public void create(@DestinationVariable Long planId, @Valid RouteRequest request) {

        routeService.create(planId, request);
        sendFullRoutes(planId);
    }

    @MessageMapping("/{routeId}/update")
    public void update(
            @DestinationVariable Long planId,
            @DestinationVariable Long routeId,
            @Valid RouteRequest request
    ) {
        routeService.update(planId, routeId, request);
        sendFullRoutes(planId);
    }

    @MessageMapping("{routeId}/delete")
    public void delete(@DestinationVariable Long planId, @DestinationVariable Long routeId) {
        routeService.delete(planId,routeId);
        sendFullRoutes(planId);
    }


    //해당 플랜의 전체 Route 리스트 브로드캐스트
    private void sendFullRoutes(Long planId) {
        List<Route> routes = routeService.getRoutes(planId);
        messagingTemplate.convertAndSend(
                "/topic/plans/" + planId + "/routes",
                Map.of(
                        "type", "FULL_UPDATE",
                        "routes", routes.stream().map(RouteResponse::from).toList()
                )
        );
    }
}