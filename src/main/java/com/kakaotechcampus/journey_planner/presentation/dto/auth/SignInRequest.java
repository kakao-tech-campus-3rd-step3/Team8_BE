package com.kakaotechcampus.journey_planner.presentation.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "이름은 비워둘 수 없습니다.")
        String name,
        @NotBlank(message = "전화번호는 비워둘 수 없습니다.")
        String contact,
        @NotBlank(message = "이메일은 비워둘 수 없습니다.")
        String email,
        @NotBlank(message = "비밀번호는 비워둘 수 없습니다.")
        String password,
        @NotBlank(message = "MBTI는 비워둘 수 없습니다.")
        String mbti) {
}
