package com.kakaotechcampus.journey_planner.application.auth;

import com.kakaotechcampus.journey_planner.application.auth.jwt.JwtProvider;
import com.kakaotechcampus.journey_planner.application.auth.utils.PasswordEncodeUtil;
import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.member.MemberRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.request.LoginRequestDto;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.request.SignUpRequestDto;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncodeUtil passwordEncodeUtil;

    public TokenResponseDto signIn(SignUpRequestDto signUpRequestDto) {
        String email = signUpRequestDto.email();
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED);
        }

        Member member = signUpRequestDto.toEntity();
        passwordEncodeUtil.passwordEncoding(member);

        Member savedMember = memberRepository.save(member);
        if (!memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        String accessToken = jwtProvider.generateAccessToken(savedMember);
        String refreshToken = jwtProvider.generateRefreshToken(savedMember);
        return new TokenResponseDto(accessToken, refreshToken);
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.email();
        String password = loginRequestDto.password();
      
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        if (!member.verifyPassword(password)) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);
        return new TokenResponseDto(accessToken, refreshToken);
    }

    public TokenResponseDto refresh(String refreshToken) {
        String email = jwtProvider.extractEmailFromRefreshToken(refreshToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        String newAccessToken = jwtProvider.generateAccessToken(member);
        String newRefreshToken = jwtProvider.generateRefreshToken(member);
        // TODO : Redis에 저장된 기존 리프레시 토큰을 새로운 토큰으로 업데이트
        return new TokenResponseDto(newAccessToken, newRefreshToken);
    }
}
