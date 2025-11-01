package com.kakaotechcampus.journey_planner.application.waypoint;

import com.kakaotechcampus.journey_planner.application.plan.PlanService;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.domain.waypoint.WaypointMapper;
import com.kakaotechcampus.journey_planner.domain.waypoint.repository.WaypointRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.infra.message.publisher.MessagePublisherManager;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WayPointModifyDto;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WaypointRequestDto;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.response.WaypointResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaypointService {
    private final MessagePublisherManager<Waypoint> messagePublisherManager;
    private final PlanService planService;
    private final WaypointRepository waypointRepository;

    @Transactional
    public void createWaypoint(Long planId, WaypointRequestDto request) {
        Plan plan = planService.getPlanEntity(planId);
        Waypoint waypoint = WaypointMapper.toEntity(request);
        plan.addWaypoint(waypoint);
        messagePublisherManager.controlNode(planId, waypoint, Waypoint.class);
    }

    @Transactional(readOnly = true)
    public Waypoint getWaypointEntity(Long id) {
        return waypointRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<WaypointResponseDto> getWaypoints(Long planId) {
        Plan plan = planService.getPlanEntity(planId);

        List<Waypoint> waypoints = waypointRepository.findAllByPlanId(plan.getId());
        return WaypointMapper.toResponseList(waypoints);
    }

    @Transactional
    public WaypointResponseDto updateWaypoint(Long planId, Long waypointId, WayPointModifyDto request) {
        Waypoint waypoint = waypointRepository.findByIdAndPlanId(waypointId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));
        waypoint.update(request);
        return WaypointResponseDto.of(waypoint);
    }

    @Transactional
    public void deleteWaypoint(Long planId, Long waypointId) {
        Plan plan = planService.getPlanEntity(planId);
        Waypoint waypoint = waypointRepository.findByIdAndPlanId(waypointId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND));
        plan.removeWaypoint(waypoint);
    }

    // planId에 속한 모든 waypoint 조회
    @Transactional(readOnly = true)
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