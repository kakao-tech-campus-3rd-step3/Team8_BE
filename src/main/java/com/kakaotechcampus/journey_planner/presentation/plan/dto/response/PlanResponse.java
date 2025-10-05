package com.kakaotechcampus.journey_planner.presentation.plan.dto.response;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.presentation.traveler.dto.response.TravelerResponse;

import java.time.LocalDate;
import java.util.List;

public record PlanResponse(
        Long id,
        String title,
        String description,
        List<TravelerResponse> travelers,
        LocalDate startDate,
        LocalDate endDate
) {
    public static PlanResponse of(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.getTravelers().stream().map(TravelerResponse::to).toList(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }
}
