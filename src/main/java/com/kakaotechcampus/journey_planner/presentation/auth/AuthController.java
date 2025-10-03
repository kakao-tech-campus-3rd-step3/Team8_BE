package com.kakaotechcampus.journey_planner.presentation.auth;

import com.kakaotechcampus.journey_planner.application.auth.AuthService;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.request.LoginRequestDto;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.request.SignUpRequestDto;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.response.TokenResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final String REFRESH_TOKEN = "Refresh-Token";

    @PostMapping("/signup")
    private ResponseEntity<TokenResponseDto> signInAndLogin(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signIn(signUpRequestDto));
    }

    @PostMapping("/login")
    private ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
    }

    @PostMapping("/refresh")
    private ResponseEntity<TokenResponseDto> refresh(@RequestHeader(REFRESH_TOKEN) String refreshToken) {
        TokenResponseDto tokenResponseDto = authService.refresh(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponseDto);
    }
}
