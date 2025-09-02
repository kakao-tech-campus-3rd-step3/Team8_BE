package com.kakaotechcampus.journey_planner.presentation.dto;

import java.time.LocalDate;

public record CreatePlanRequest(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {}
