package com.kakaotechcampus.journey_planner.presentation.plan.dto.response;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.traveler.Traveler;

import java.time.LocalDate;
import java.util.List;

public record PlanResponse(
        Long id,
        String title,
        String description,
        Member organizer,
        List<Traveler> travelers,
        LocalDate startDate,
        LocalDate endDate
) {
    public static PlanResponse of(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.getOrganizer(),
                plan.getTravelers(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }
}
