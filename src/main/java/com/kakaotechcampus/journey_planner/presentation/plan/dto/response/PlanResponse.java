package com.kakaotechcampus.journey_planner.presentation.plan.dto.response;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.memberplan.MemberPlan;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import java.time.LocalDate;
import java.util.List;

public record PlanResponse(
        Long id,
        String title,
        String description,
        Member organizer,
        List<MemberPlan> memberPlans,
        LocalDate startDate,
        LocalDate endDate
) {
    public static PlanResponse of(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.getOrganizer(),
                plan.getMemberPlans(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }
}
