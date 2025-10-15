package com.kakaotechcampus.journey_planner.application;

import com.kakaotechcampus.journey_planner.application.auth.AuthService;
import com.kakaotechcampus.journey_planner.application.token.TokenService;
import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.member.repository.MemberRepository;
import com.kakaotechcampus.journey_planner.domain.token.TokenType;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.request.SignUpRequestDto;
import com.kakaotechcampus.journey_planner.presentation.auth.dto.response.TokenResponseDto;
import com.kakaotechcampus.journey_planner.support.MemberTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("회원가입(Sign up) 테스트")
    class SignUpTest {
        @Test
        @DisplayName("성공")
        void signUpSuccess() {
            // given
            SignUpRequestDto requestDto = new SignUpRequestDto("김철수", "010-1234-1234", "test@example.com", "password123", "ENTP");
            Member savedMember = MemberTestBuilder.aMember()
                    .withId(1L)
                    .withName("김철수")
                    .withContact("010-1234-1234")
                    .withEmail("test@example.com")
                    .withPassword("encodedPassword")
                    .withMbti("ENTP").build();
            // mock 설정
            when(memberRepository.existsByEmail(anyString())).thenReturn(false);
            when(memberRepository.save(any(Member.class))).thenReturn(savedMember);
            when(tokenService.generateToken(eq(TokenType.ACCESS), eq(1L))).thenReturn("accessToken");
            when(tokenService.generateToken(eq(TokenType.REFRESH), eq(1L))).thenReturn("refreshToken");
            // when
            TokenResponseDto responseDto = authService.signUp(requestDto);
            // then
            assertThat(responseDto.accessToken()).isEqualTo("accessToken");
            assertThat(responseDto.refreshToken()).isEqualTo("refreshToken");
        }

        @Test
        @DisplayName("실패 - 이미 등록된 이메일")
        void signUpFail_AlreadyRegistered() {
            // given
            SignUpRequestDto requestDto = new SignUpRequestDto("김철수", "010-1234-1234", "test@example.com", "password123", "ENTP");
            when(memberRepository.existsByEmail(anyString())).thenReturn(true);

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> {
                authService.signUp(requestDto);
            });
            assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(exception.getCode()).isEqualTo("ME_002");
            assertThat(exception.getMessage()).isEqualTo("이미 존재하는 멤버입니다.");
        }
    }

    @Nested
    @DisplayName("로그인(login) 테스트")
    class LoginTest {
        void loginSuccess() {

        }
    }
}
