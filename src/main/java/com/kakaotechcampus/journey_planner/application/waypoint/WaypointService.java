package com.kakaotechcampus.journey_planner.application.waypoint;

import com.kakaotechcampus.journey_planner.application.plan.PlanService;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.domain.waypoint.WaypointMapper;
import com.kakaotechcampus.journey_planner.domain.waypoint.repository.WaypointRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WaypointRequest;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.response.WaypointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaypointService {

    private final PlanService planService;
    private final WaypointRepository waypointRepository;

    // planId에 해당하는 plan에 waypoint 추가
    @Transactional
    public WaypointResponse createWaypoint(Long planId, WaypointRequest request) {
        Waypoint waypoint = WaypointMapper.toEntity(request);
        waypoint.validateCategory(waypoint.getLocationCategory(),waypoint.getLocationSubCategory());

        Plan plan = planService.getPlanEntity(planId);

        // 단방향: Waypoint 쪽에 Plan을 세팅
        waypoint.assignToPlan(plan);
        Waypoint savedWaypoint = waypointRepository.save(waypoint);

        return WaypointMapper.toResponse(savedWaypoint);
    }

    @Transactional
    public WaypointResponse updateWaypoint(Long planId, Long waypointId, WaypointRequest request) {
        Waypoint waypoint = waypointRepository.findByIdAndPlanId(waypointId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));
        waypoint.validateCategory(waypoint.getLocationCategory(),waypoint.getLocationSubCategory());

        waypoint.update(
                request.name(),
                request.description(),
                request.address(),
                request.startTime(),
                request.endTime(),
                request.locationCategory(),
                request.locationSubCategory(),
                request.xPosition(),
                request.yPosition()
        );

        return WaypointMapper.toResponse(waypoint);
    }

    // planId 및 waypointId에 해당하는 waypoint 삭제
    @Transactional
    public void deleteWaypoint(Long planId, Long waypointId) {
        Waypoint waypoint = waypointRepository.findByIdAndPlanId(waypointId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));

        waypointRepository.delete(waypoint);
    }

    // planId에 속한 모든 waypoint 조회
    public List<WaypointResponse> getWaypoints(Long planId) {
        Plan plan = planService.getPlanEntity(planId);

        List<Waypoint> waypoints = waypointRepository.findAllByPlanId(plan.getId());
        return WaypointMapper.toResponseList(waypoints);
    }

    @Transactional(readOnly = true)
    public Waypoint getWaypointEntity(Long id) {
        return waypointRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));
    }
}