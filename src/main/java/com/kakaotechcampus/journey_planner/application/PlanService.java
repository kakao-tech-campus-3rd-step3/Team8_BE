package com.kakaotechcampus.journey_planner.application;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.presentation.dto.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.PlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new IllegalArgumentException("해당하는 계획을 찾을 수 없습니다."));
        return new PlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }
}
