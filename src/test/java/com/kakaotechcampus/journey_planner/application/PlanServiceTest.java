package com.kakaotechcampus.journey_planner.application;

import com.kakaotechcampus.journey_planner.application.plan.PlanService;
import com.kakaotechcampus.journey_planner.application.traveler.TravelerService;
import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.member.repository.MemberRepository;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.repository.JpaPlanRepository;
import com.kakaotechcampus.journey_planner.domain.plan.repository.PlanRepository;
import com.kakaotechcampus.journey_planner.domain.traveler.Traveler;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.PlanResponse;
import com.kakaotechcampus.journey_planner.support.MemberTestBuilder;
import com.kakaotechcampus.journey_planner.support.PlanTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {
    @InjectMocks
    private PlanService planService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JpaPlanRepository jpaPlanRepository;
    @Mock
    private PlanRepository planRepository;
    @Mock
    private TravelerService travelerService;

    @Nested
    @DisplayName("여행 계획 생성(createPlan) 테스트")
    class CreatePlanTest {
        @Test
        @DisplayName("성공")
        void createPlanSuccess() {
            // given
            Long memberId = 1L;
            CreatePlanRequest request = new CreatePlanRequest("서울 여행", "서울로 재미난 여행을 떠나보아요.", LocalDate.now(), LocalDate.now().plusDays(2));
            Member member = MemberTestBuilder.aMember().withId(memberId).build();
            Plan plan = PlanTestBuilder.aPlan().build();
            Traveler traveler = mock(Traveler.class);

            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            given(planRepository.save(any(Plan.class))).willReturn(plan);
            given(travelerService.createOwnerTraveler(member, plan)).willReturn(traveler);

            // when
            PlanResponse response = planService.createPlan(memberId, request);

            // then
            assertThat(response).isNotNull();
            assertThat(response.title()).isEqualTo("서울 여행");
            then(member).should().addTraveler(traveler);
        }

        @Test
        @DisplayName("실패 - 사용자를 찾을 수 없음")
        void createPlanFail_MemberNotFound() {
            // given
            Long memberId = 999L;
            CreatePlanRequest request = new CreatePlanRequest("서울 여행", "서울로 재미난 여행을 떠나보아요.", LocalDate.now(), LocalDate.now().plusDays(2));
            given(memberRepository.findById(memberId)).willReturn(Optional.empty());

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () ->
                    planService.createPlan(memberId, request));
            assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(exception.getCode()).isEqualTo("ME_001");
            assertThat(exception.getMessage()).isEqualTo("해당 멤버를 찾을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("여행 계획 단건 조회(getPlan) 테스트")
    class GetPlanTest {
        @Test
        @DisplayName("성공")
        void getPlanSuccess() {
            // given
            Long memberId = 1L;
            Long planId = 1L;
            Member member = MemberTestBuilder.aMember().withId(memberId).build();
            Plan plan = mock(Plan.class);

            given(planRepository.findById(planId)).willReturn(Optional.of(plan));
            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            given(plan.hasMember(member)).willReturn(true);
            given(plan.getTitle()).willReturn("제주도 여행");

            // when
            PlanResponse response = planService.getPlan(memberId, planId);

            // then
            assertThat(response).isNotNull();
            assertThat(response.title()).isEqualTo("제주도 여행");
        }

        @Test
        @DisplayName("실패 - 계획에 접근 권한 없음")
        void getPlanFail_AccessDenied() {
            // given
            Long memberId = 1L;
            Long planId = 1L;
            Member member = MemberTestBuilder.aMember().withId(memberId).build();
            Plan plan = mock(Plan.class);

            given(planRepository.findById(planId)).willReturn(Optional.of(plan));
            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            given(plan.hasMember(member)).willReturn(false); // 권한이 없는 것으로 설정

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () ->
                    planService.getPlan(memberId, planId));
            assertThat(exception.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
            assertThat(exception.getCode()).isEqualTo("PE_002");
            assertThat(exception.getMessage()).isEqualTo("해당 계획에 접근할 권한이 없습니다.");
        }
    }

    @Nested
    @DisplayName("사용자 초대(inviteMember) 테스트")
    class InviteMemberTest {
        @Test
        @DisplayName("성공")
        void inviteMemberSuccess() {
            // given
            Long inviterId = 1L;
            Long planId = 1L;
            String inviteeEmail = "invitee@example.com";
            Plan plan = mock(Plan.class);
            Member inviter = MemberTestBuilder.aMember().withId(inviterId).build();
            Member invitee = MemberTestBuilder.aMember().withId(2L).withEmail(inviteeEmail).build();
            Traveler traveler = mock(Traveler.class);

            given(planRepository.findById(planId)).willReturn(Optional.of(plan));
            given(memberRepository.findById(inviterId)).willReturn(Optional.of(inviter));
            given(travelerService.isOwner(plan, inviter)).willReturn(true); // 초대한 사람이 소유주
            given(memberRepository.findByEmail(inviteeEmail)).willReturn(Optional.of(invitee));
            given(plan.hasMember(invitee)).willReturn(false); // 아직 계획에 포함되지 않은 멤버
            given(travelerService.addTraveler(any(Traveler.class))).willReturn(traveler);

            // when
            planService.inviteMember(inviterId, planId, inviteeEmail);

            // then
            then(travelerService).should(times(1)).addTraveler(any(Traveler.class));
        }

        @Test
        @DisplayName("실패 - 초대하는 사람이 소유주가 아님")
        void inviteMemberFail_NotOwner() {
            // given
            Long inviterId = 1L;
            Long planId = 1L;
            String inviteeEmail = "invitee@example.com";
            Plan plan = mock(Plan.class);
            Member inviter = MemberTestBuilder.aMember().withId(inviterId).build();

            given(planRepository.findById(planId)).willReturn(Optional.of(plan));
            given(memberRepository.findById(inviterId)).willReturn(Optional.of(inviter));
            given(travelerService.isOwner(plan, inviter)).willReturn(false); // 소유주가 아님

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () ->
                    planService.inviteMember(inviterId, planId, inviteeEmail));
            assertThat(exception.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
            assertThat(exception.getCode()).isEqualTo("PE_002");
            assertThat(exception.getMessage()).isEqualTo("해당 계획에 접근할 권한이 없습니다.");
        }
    }
}
