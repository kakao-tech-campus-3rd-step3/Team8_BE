package com.kakaotechcampus.journey_planner.application.plan;

import com.kakaotechcampus.journey_planner.application.memberplan.MemberPlanService;
import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.memberplan.MemberPlan;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanMapper;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.PlanResponse;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.UpdatePlanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final MemberPlanService memberPlanService;

    @Transactional
    public PlanResponse createPlan(Member member, CreatePlanRequest request) {
        Plan plan = PlanMapper.toEntity(request);
        Plan savedPlan = planRepository.save(plan);
        MemberPlan savedMemberPlan = memberPlanService.createMemberPlan(member, savedPlan);
        member.addMemberPlan(savedMemberPlan);
        plan.addMemberPlan(savedMemberPlan);
        return PlanMapper.toResponse(savedPlan);
    }

    @Transactional(readOnly = true)
    public PlanResponse getPlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
        return PlanMapper.toResponse(plan);
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getAllPlans() {
        return PlanMapper.toResponseList(planRepository.findAll());
    }

    @Transactional
    public PlanResponse updatePlan(Long id, UpdatePlanRequest request) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));

        plan.update(
                request.title(),
                request.description(),
                request.startDate(),
                request.endDate()
        );

        return PlanMapper.toResponse(plan);
    }

    @Transactional
    public void deletePlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
        planRepository.delete(plan);
    }

    @Transactional(readOnly = true)
    public Plan getPlanEntity(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
    }
}
