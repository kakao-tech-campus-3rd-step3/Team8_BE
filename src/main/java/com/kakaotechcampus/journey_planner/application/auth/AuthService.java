package com.kakaotechcampus.journey_planner.application.auth;

import com.kakaotechcampus.journey_planner.application.auth.jwt.JwtProvider;
import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.member.MemberRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.dto.auth.LoginRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.auth.SignInRequest;
import com.kakaotechcampus.journey_planner.presentation.dto.auth.TokenResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public AuthService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    public TokenResponse signIn(SignInRequest signInRequest) {
        String email = signInRequest.email();
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED);
        }
        Member member = new Member(signInRequest.name(), signInRequest.contact(), email, signInRequest.password(), signInRequest.mbti());
        memberRepository.save(member);
        if (!memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member1 = memberRepository.findByEmail(member.getEmail()).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        String accessToken = jwtProvider.generateAccessToken(member1);
        String refreshToken = jwtProvider.generateRefreshToken(member1);
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse login(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();
        if (!memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        if (!member.verifyPassword(password)) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refresh(String refreshToken) {
        String email = jwtProvider.extractEmailFromRefreshToken(refreshToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        String newAccessToken = jwtProvider.generateAccessToken(member);
        String newRefreshToken = jwtProvider.generateRefreshToken(member);
        // TODO : Redis에 저장된 기존 리프레시 토큰을 새로운 토큰으로 업데이트
        return new TokenResponse(newAccessToken, newRefreshToken);
    }

}
