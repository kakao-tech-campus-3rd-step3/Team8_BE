package com.kakaotechcampus.journey_planner.presentation.traveler.dto.response;

import com.kakaotechcampus.journey_planner.domain.member.MbtiType;
import com.kakaotechcampus.journey_planner.domain.traveler.InvitationStatus;
import com.kakaotechcampus.journey_planner.domain.traveler.Role;

public record TravelerResponse(
        Long id,
        Long memberId,
        String name,
        String contact,
        MbtiType mbtiType,
        InvitationStatus status,
        Role role
) {
}
