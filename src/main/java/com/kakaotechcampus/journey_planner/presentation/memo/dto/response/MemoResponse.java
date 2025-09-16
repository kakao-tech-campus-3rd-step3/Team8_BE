package com.kakaotechcampus.journey_planner.presentation.memo.dto.response;

public record MemoResponse(
        Long id,
        String title,
        String content,
        Float xPosition,
        Float yPosition
) {}
