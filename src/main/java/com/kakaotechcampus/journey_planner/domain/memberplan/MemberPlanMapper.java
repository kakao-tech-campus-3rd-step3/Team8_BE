package com.kakaotechcampus.journey_planner.domain.memberplan;

import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.InvitationResponse;

import java.util.List;

public class MemberPlanMapper {
    public static List<InvitationResponse> toInvitationResponse(List<MemberPlan> memberPlans) {
        return memberPlans.stream()
                .map(memberPlan -> new InvitationResponse(
                        memberPlan.getPlan().getId(),
                        memberPlan.getPlan().getTitle(),
                        memberPlan.getPlan().getOrganizer().getName(),
                        memberPlan.getId(),
                        memberPlan.getStatus().toString()
                ))
                .toList();
    }
}
