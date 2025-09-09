package com.kakaotechcampus.journey_planner.application.route;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.domain.route.Route;
import com.kakaotechcampus.journey_planner.domain.route.RouteRepository;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.domain.waypoint.WaypointRepository;
import com.kakaotechcampus.journey_planner.global.exception.CustomRuntimeException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.dto.route.RouteRequest;
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
    public List<Route> getRoutes(Long planId) {
        planRepository.findById(planId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PLAN_NOT_FOUND));
        return routeRepository.findAllByPlan_Id(planId);
    }

    public Route create(Long planId, RouteRequest req) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PLAN_NOT_FOUND));


        //에러코드 만들어야함
        Waypoint from = waypointRepository.findById(req.fromWaypointId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.NO_FILE));
        Waypoint to = waypointRepository.findById(req.toWaypointId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.NO_FILE));

        // 같은 플랜 소속인지 검증
        if (!from.getPlan().getId().equals(planId) || !to.getPlan().getId().equals(planId)) {
            throw new CustomRuntimeException(ErrorCode.INVALID_INPUT, "웨이포인트가 해당 플랜에 속해있지 않습니다.");
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
}
