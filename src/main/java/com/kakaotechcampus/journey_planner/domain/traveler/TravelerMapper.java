package com.kakaotechcampus.journey_planner.domain.traveler;

import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.InvitationResponse;

import java.util.List;

public class TravelerMapper {
    public static List<InvitationResponse> toInvitationResponse(List<Traveler> travelers) {
        return travelers.stream()
                .map(traveler -> new InvitationResponse(
                        traveler.getPlan().getId(),
                        traveler.getPlan().getTitle(),
                        traveler.getPlan().getOrganizer().getName(),
                        traveler.getId(),
                        traveler.getStatus().toString()
                ))
                .toList();
    }
}
