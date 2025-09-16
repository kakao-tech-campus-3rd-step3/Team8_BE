package com.kakaotechcampus.journey_planner.application.route;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.domain.route.Route;
import com.kakaotechcampus.journey_planner.domain.route.RouteRepository;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.domain.waypoint.WaypointRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.route.dto.request.RouteRequest;
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

    public List<Route> getRoutes(Long planId) {
        planRepository.findById(planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
        return routeRepository.findAllByPlanId(planId);
    }

    public Route create(Long planId, RouteRequest req) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));


        Waypoint from = waypointRepository.findById(req.fromWaypointId())
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));
        Waypoint to = waypointRepository.findById(req.toWaypointId())
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));

        // 같은 플랜 소속인지 검증
        if (!from.getPlan().getId().equals(planId) || !to.getPlan().getId().equals(planId)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "웨이포인트가 해당 플랜에 속해있지 않습니다.");
        }

        Route saved = routeRepository.save(
                new Route(
                        plan,
                        from,
                        to,
                        req.title(),
                        req.description(),
                        req.duration(),
                        req.vehicleCategory()
                )
        );
        return saved;
    }

    @Transactional
    public Route update(Long planId, Long routeId, RouteRequest request) {
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

        return route;
    }

    public void delete(Long planId, Long routeId) {
        Route route=routeRepository.findByIdAndPlanId(routeId,planId).orElseThrow(() -> new BusinessException(ErrorCode.ROUTE_NOT_FOUND));
        routeRepository.delete(route);


    }



}
