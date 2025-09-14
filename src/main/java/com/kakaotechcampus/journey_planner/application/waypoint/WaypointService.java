package com.kakaotechcampus.journey_planner.application.waypoint;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.domain.waypoint.WaypointRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.dto.waypoint.WaypointRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WaypointService {

    private final PlanRepository planRepository;
    private final WaypointRepository waypointRepository;

    public WaypointService(PlanRepository planRepository, WaypointRepository waypointRepository) {
        this.planRepository = planRepository;
        this.waypointRepository = waypointRepository;
    }

     // planId에 해당하는 plan에 waypoint 추가
    @Transactional
    public void addWaypoint(Long planId, Waypoint waypoint) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));

        // 단방향: Waypoint 쪽에 Plan을 세팅
        waypoint.assignToPlan(plan);
        waypointRepository.save(waypoint);
    }

    @Transactional
    public void updateWaypoint(Long planId, Long waypointId, WaypointRequest request) {
        Waypoint waypoint = waypointRepository.findByIdAndPlanId(waypointId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));

        waypoint.update(
                request.name(),
                request.description(),
                request.address(),
                request.startTime(),
                request.endTime(),
                request.locationCategory(),
                request.xPosition(),
                request.yPosition()
        );
    }

     // planId 및 waypointId에 해당하는 waypoint 삭제
    @Transactional
    public void removeWaypoint(Long planId, Long waypointId) {
        Waypoint waypoint = waypointRepository.findByIdAndPlanId(waypointId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));

        waypointRepository.delete(waypoint);
    }


    // planId에 속한 모든 waypoint 조회
    @Transactional(readOnly = true)
    public List<Waypoint> getWaypoints(Long planId) {
        return waypointRepository.findAllByPlanId(planId);
    }
}