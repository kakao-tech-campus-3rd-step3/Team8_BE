package com.kakaotechcampus.journey_planner.application.route;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.domain.route.Route;
import com.kakaotechcampus.journey_planner.domain.route.RouteMapper;
import com.kakaotechcampus.journey_planner.domain.route.RouteRepository;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.domain.waypoint.WaypointRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.route.dto.request.RouteRequest;
import com.kakaotechcampus.journey_planner.presentation.route.dto.response.RouteResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final PlanRepository planRepository;
    private final WaypointRepository waypointRepository;
    private final RouteRepository routeRepository;

    @Transactional
    public RouteResponse create(Long planId, RouteRequest request) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));

        Waypoint fromWaypoint = waypointRepository.findById(request.fromWaypointId())
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));

        Waypoint toWaypoint = waypointRepository.findById(request.toWaypointId())
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));


        validateWaypointsBelongToPlan(planId, fromWaypoint, toWaypoint);

        Route route = RouteMapper.toEntity(plan, fromWaypoint, toWaypoint, request);

        Route savedRoute = routeRepository.save(route);
        return RouteMapper.toResponse(savedRoute);
    }

    @Transactional
    public RouteResponse update(Long planId, Long routeId, RouteRequest request) {
        Route route = routeRepository.findByIdAndPlanId(routeId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTE_NOT_FOUND));

        Waypoint from = waypointRepository.findById(request.fromWaypointId())
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));
        Waypoint to = waypointRepository.findById(request.toWaypointId())
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));

        route.update(
                from,
                to,
                request.title(),
                request.description(),
                request.duration(),
                request.vehicleCategory()
        );

        return RouteMapper.toResponse(route);
    }

    @Transactional
    public void delete(Long planId, Long routeId) {
        Route route = routeRepository.findByIdAndPlanId(routeId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTE_NOT_FOUND));

        routeRepository.delete(route);
    }

    // planId에 속한 모든 route 조회
    public List<RouteResponse> getRoutes(Long planId) {
        if (planRepository.existsById(planId)) {
            List<Route> routes = routeRepository.findAllByPlanId(planId);
            return RouteMapper.toResponseList(routes);
        }
        throw new BusinessException(ErrorCode.PLAN_NOT_FOUND);
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
}
