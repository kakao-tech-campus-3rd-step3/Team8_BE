package com.kakaotechcampus.journey_planner.presentation.dto.memo;

public record MemoResponse(
        Long id,
        String title,
        String content,
        Float xPosition,
        Float yPosition
) {}
