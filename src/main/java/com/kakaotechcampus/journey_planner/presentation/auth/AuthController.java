package com.kakaotechcampus.journey_planner.presentation.auth;

import com.kakaotechcampus.journey_planner.application.auth.AuthService;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.request.LoginRequestDto;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.request.SignUpRequestDto;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.response.TokenResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/v1/members/signup")
    private ResponseEntity<TokenResponseDto> signInAndLogin(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signIn(signUpRequestDto));
    }

    @PostMapping("/v1/members/login")
    private ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
    }

    @PostMapping("/v1/members/refresh")
    private ResponseEntity<TokenResponseDto> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
        TokenResponseDto tokenResponseDto = authService.refresh(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponseDto);
    }
}
