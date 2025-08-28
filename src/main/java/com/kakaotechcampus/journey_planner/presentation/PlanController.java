package com.kakaotechcampus.journey_planner.presentation;

import com.kakaotechcampus.journey_planner.application.PlanService;
import com.kakaotechcampus.journey_planner.presentation.dto.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.PlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/plans")
public class PlanController {

    private final PlanService planService;

    // Plan 생성
    @PostMapping
    public ResponseEntity<ApiResponse<PlanResponse>> createPlan(@RequestBody CreatePlanRequest request) {
        PlanResponse response = planService.createPlan(request);
        return ResponseEntity.status(200).body(new ApiResponse<>(201, response));
    }

    // Plan 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanResponse>> getPlan(@PathVariable Long id) {
        PlanResponse response = planService.getPlan(id);
        return ResponseEntity.ok(new ApiResponse<>(200, response));
    }
}
