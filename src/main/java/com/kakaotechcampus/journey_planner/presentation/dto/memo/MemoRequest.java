package com.kakaotechcampus.journey_planner.presentation.dto.memo;

public record MemoRequest(
    String title,
    String content,
    Float xPosition,
    Float yPosition
) {}
