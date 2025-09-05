package com.kakaotechcampus.journey_planner.presentation.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "이메일은 비워둘 수 없습니다.")
        String email,
        @NotBlank(message = "비밀번호는 비워둘 수 없습니다.")
        String password
) {
}
