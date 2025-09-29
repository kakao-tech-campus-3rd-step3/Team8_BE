package com.kakaotechcampus.journey_planner.presentation.traveler.dto.response;

import com.kakaotechcampus.journey_planner.domain.member.MbtiType;

public record TravelerResponse(
        Long id,
        Long memberId,
        String name,
        String contact,
        MbtiType mbtiType

) {
}
