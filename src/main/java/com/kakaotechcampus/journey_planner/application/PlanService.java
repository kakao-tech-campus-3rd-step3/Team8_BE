package com.kakaotechcampus.journey_planner.application;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.global.exception.PlanNotFoundException;
import com.kakaotechcampus.journey_planner.presentation.dto.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.PlanResponse;
import com.kakaotechcampus.journey_planner.presentation.dto.UpdatePlanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    @Transactional
    public PlanResponse createPlan(CreatePlanRequest request) {
        Plan plan = new Plan(
                request.title(),
                request.description(),
                request.startDate(),
                request.endDate()
        );
        Plan saved = planRepository.save(plan);
        return new PlanResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStartDate(),
                saved.getEndDate()
        );
    }

    @Transactional
    public PlanResponse getPlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException("해당하는 계획을 찾을 수 없습니다."));
        return new PlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getAllPlans() {
        return planRepository.findAll().stream()
                .map(plan -> new PlanResponse(
                        plan.getId(),
                        plan.getTitle(),
                        plan.getDescription(),
                        plan.getStartDate(),
                        plan.getEndDate()
                ))
                .toList();
    }

    @Transactional
    public PlanResponse updatePlan(Long id, UpdatePlanRequest request) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException("해당하는 계획을 찾을 수 없습니다."));

        plan.update(
                request.title(),
                request.description(),
                request.startDate(),
                request.endDate()
        );

        return new PlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }

    @Transactional
    public void deletePlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException("해당하는 계획을 찾을 수 없습니다."));
        planRepository.delete(plan);
    }

}
