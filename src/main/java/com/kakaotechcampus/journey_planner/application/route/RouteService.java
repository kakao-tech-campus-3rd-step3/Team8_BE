package com.kakaotechcampus.journey_planner.application.route;

import com.kakaotechcampus.journey_planner.application.plan.PlanService;
import com.kakaotechcampus.journey_planner.application.waypoint.WaypointService;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.route.Route;
import com.kakaotechcampus.journey_planner.domain.route.RouteMapper;
import com.kakaotechcampus.journey_planner.domain.route.repository.RouteRepository;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.route.dto.request.RouteRequest;
import com.kakaotechcampus.journey_planner.presentation.route.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final PlanService planService;
    private final WaypointService waypointService;

    @Transactional
    public RouteResponse createRoute(Long planId, RouteRequest request) {

        Plan plan = planService.getPlanEntity(planId);
        Waypoint fromWaypoint = waypointService.getWaypointEntity(request.fromWaypointId());
        Waypoint toWaypoint = waypointService.getWaypointEntity(request.toWaypointId());

        validateWaypointsBelongToPlan(planId, fromWaypoint, toWaypoint);

        Route route = RouteMapper.toEntity(plan, fromWaypoint, toWaypoint, request);

        Route savedRoute = routeRepository.save(route);
        return RouteMapper.toResponse(savedRoute);
    }

    @Transactional
    public RouteResponse updateRoute(Long planId, Long routeId, RouteRequest request) {
        Route route = routeRepository.findByIdAndPlanId(routeId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTE_NOT_FOUND));

        Waypoint fromWaypoint = waypointService.getWaypointEntity(request.fromWaypointId());
        Waypoint toWaypoint = waypointService.getWaypointEntity(request.toWaypointId());

        route.update(
                fromWaypoint,
                toWaypoint,
                request.title(),
                request.description(),
                request.duration(),
                request.vehicleCategory()
        );

        return RouteMapper.toResponse(route);
    }

    @Transactional
    public void deleteRoute(Long planId, Long routeId) {
        Route route = routeRepository.findByIdAndPlanId(routeId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTE_NOT_FOUND));

        routeRepository.delete(route);
    }

    // planId에 속한 모든 route 조회
    public List<RouteResponse> getRoutes(Long planId) {
        Plan plan = planService.getPlanEntity(planId);

        List<Route> routes = routeRepository.findAllByPlanId(plan.getId());
        return RouteMapper.toResponseList(routes);
    }


    private void validateWaypointsBelongToPlan(Long planId, Waypoint fromWaypoint, Waypoint toWaypoint) {
        if (!fromWaypoint.getPlan().getId().equals(planId) ||
                !toWaypoint.getPlan().getId().equals(planId)) {
            throw new BusinessException(
                    ErrorCode.INVALID_INPUT,
                    "웨이포인트가 해당 플랜에 속해있지 않습니다."
            );
        }
    }

    @Transactional(readOnly = true)
    public Route getRouteEntity(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTE_NOT_FOUND));
    }
}
