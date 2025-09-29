package com.kakaotechcampus.journey_planner.presentation.plan;

import com.kakaotechcampus.journey_planner.application.plan.PlanService;
import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.global.annotation.LoginMember;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.InvitationResponse;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.PlanResponse;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.UpdatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.traveler.dto.response.TravelerResponse;
import com.kakaotechcampus.journey_planner.presentation.utils.MessagingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/plans")
public class PlanController {
    private final PlanService planService;
    private final MessagingUtil messagingUtil;

    // Plan 생성
    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(
            @LoginMember Member member,
            @Valid @RequestBody CreatePlanRequest request
    ) {
        PlanResponse response = planService.createPlan(member, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Plan 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> getPlan(
            @LoginMember Member member,
            @PathVariable Long id
    ) {
        PlanResponse response = planService.getPlan(member, id);
        return ResponseEntity.ok(response);
    }

    // Plan 전체 조회
    @GetMapping
    public ResponseEntity<List<PlanResponse>> getAllPlans(@LoginMember Member member) {
        List<PlanResponse> response = planService.getAllPlans(member.getId());
        return ResponseEntity.ok(response);
    }

    // Plan 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PlanResponse> updatePlan(
            @LoginMember Member member,
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlanRequest request
    ) {
        PlanResponse response = planService.updatePlan(member, id, request);
        return ResponseEntity.ok(response);
    }

    // Plan 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(
            @LoginMember Member member,
            @PathVariable Long id
    ) {
        planService.deletePlan(member, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/invitations/{planId}")
    public ResponseEntity<Void> inviteMember(
            @LoginMember Member member,
            @PathVariable Long planId,
            @RequestParam("email") String inviteeEmail
    ){
        TravelerResponse response = planService.inviteMember(member, planId, inviteeEmail);
        messagingUtil.sendResponse(planId, "travelers", "TRAVELER_INVITE", "traveler", response);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/invitations/{invitationId}/accept")
    public ResponseEntity<Void> acceptInvitation(
            @LoginMember Member member,
            @PathVariable Long invitationId
    ) {
        planService.acceptInvitation(member, invitationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/invitations")
    public ResponseEntity<List<InvitationResponse>> getInvitations(
            @LoginMember Member member
    ) {
        List<InvitationResponse> invitations = planService.getInvitations(member);
        return ResponseEntity.status(HttpStatus.OK).body(invitations);
    }
}
