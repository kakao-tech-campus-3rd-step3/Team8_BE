package com.kakaotechcampus.journey_planner.presentation.plan;

import com.kakaotechcampus.journey_planner.application.plan.PlanService;
import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.global.annotation.LoginMember;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.PlanResponse;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.UpdatePlanRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/plans")
public class PlanController {

    private final PlanService planService;

    // Plan 생성
    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(
            @LoginMember Member member,
            @Valid @RequestBody CreatePlanRequest request
    ) {
        PlanResponse response = planService.createPlan(member, request);
        return ResponseEntity.status(201).body(response);
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
    public ResponseEntity<List<PlanResponse>> getAllPlans() {
        List<PlanResponse> response = planService.getAllPlans();
        return ResponseEntity.ok(response);
    }

    // Plan 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PlanResponse> updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlanRequest request
    ) {
        PlanResponse response = planService.updatePlan(id, request);
        return ResponseEntity.ok(response);
    }

    // Plan 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
