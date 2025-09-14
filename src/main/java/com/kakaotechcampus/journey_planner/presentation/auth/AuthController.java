package com.kakaotechcampus.journey_planner.presentation.auth;

import com.kakaotechcampus.journey_planner.application.auth.AuthService;
import com.kakaotechcampus.journey_planner.presentation.dto.auth.LoginRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.auth.SignInRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.auth.TokenResponse;
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
    private ResponseEntity<TokenResponse> signInAndLogin(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signIn(signInRequest));
    }

    @PostMapping("/v1/members/login")
    private ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }

    @PostMapping("/v1/members/refresh")
    private ResponseEntity<TokenResponse> refresh(@RequestHeader("Refresh-Token") String refreshToken){
        TokenResponse tokenResponse = authService.refresh(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }
}
