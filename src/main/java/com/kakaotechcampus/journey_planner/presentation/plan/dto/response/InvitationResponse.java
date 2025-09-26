package com.kakaotechcampus.journey_planner.presentation.plan.dto.response;

public record InvitationResponse(
        Long planId,
        String planTitle,
        String planOrganizerName,
        Long invitationId,
        String status
) {
}
