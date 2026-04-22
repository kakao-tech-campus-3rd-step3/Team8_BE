package com.kakaotechcampus.journey_planner.presentation.gift.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CreateGiftEventRequest(
        @Min(1) int totalCount,
        @Min(1) @Max(100) int discountRate
) {}
