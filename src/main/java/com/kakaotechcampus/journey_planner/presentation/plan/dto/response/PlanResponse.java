package com.kakaotechcampus.journey_planner.presentation.plan.dto.response;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;

import java.time.LocalDate;

public record PlanResponse(
        Long id,
        String title,
        String description,
        // List<Traveler> travelers,
        LocalDate startDate,
        LocalDate endDate
) {
    public static PlanResponse of(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                // plan.getTravelers(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }
}
