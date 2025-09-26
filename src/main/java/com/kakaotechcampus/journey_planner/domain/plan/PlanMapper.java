package com.kakaotechcampus.journey_planner.domain.plan;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.PlanResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PlanMapper {

    public static Plan toEntity(CreatePlanRequest request, Member organizer) {
        return new Plan(
                request.title(),
                request.description(),
                request.startDate(),
                request.endDate(),
                organizer);
    }

    public static PlanResponse toResponse(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.getStartDate(),
                plan.getEndDate());
    }

    public static List<PlanResponse> toResponseList(List<Plan> plans) {
        return plans.stream()
                .map(PlanMapper::toResponse)
                .collect(Collectors.toList());
    }
}
