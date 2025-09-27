package com.kakaotechcampus.journey_planner.application.plan;

import com.kakaotechcampus.journey_planner.application.memberplan.MemberPlanService;
import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.member.MemberRepository;
import com.kakaotechcampus.journey_planner.domain.memberplan.InvitationStatus;
import com.kakaotechcampus.journey_planner.domain.memberplan.MemberPlan;
import com.kakaotechcampus.journey_planner.domain.memberplan.MemberPlanMapper;
import com.kakaotechcampus.journey_planner.domain.memberplan.MemberPlanRepository;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanMapper;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.CreatePlanRequest;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.InvitationResponse;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.response.PlanResponse;
import com.kakaotechcampus.journey_planner.presentation.plan.dto.request.UpdatePlanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final MemberPlanService memberPlanService;
    private final MemberRepository memberRepository;
    private final MemberPlanRepository memberPlanRepository;

    @Transactional
    public PlanResponse createPlan(Member member, CreatePlanRequest request) {
        Plan plan = PlanMapper.toEntity(request, member);
        Plan savedPlan = planRepository.save(plan);
        MemberPlan savedMemberPlan = memberPlanService.createMemberPlan(member, savedPlan);
        member.addMemberPlan(savedMemberPlan);
        plan.addMemberPlan(savedMemberPlan);
        return PlanResponse.of(savedPlan);
    }

    @Transactional(readOnly = true)
    public PlanResponse getPlan(Member member, Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
        boolean isMemberInPlan = plan.hasMember(member);
        if (!isMemberInPlan) {
            throw new BusinessException(ErrorCode.PLAN_ACCESS_DENIED);
        }
        return PlanResponse.of(plan);
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getAllPlans(Long userId) {
        return PlanMapper.toResponseList(planRepository.getAllPlanByUserId(userId));
    }

    @Transactional
    public PlanResponse updatePlan(Member member, Long id, UpdatePlanRequest request) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
        boolean isMemberInPlan = plan.hasMember(member);
        if (!isMemberInPlan) {
            throw new BusinessException(ErrorCode.PLAN_ACCESS_DENIED);
        }
        plan.update(
                request.title(),
                request.description(),
                request.startDate(),
                request.endDate()
        );

        return PlanResponse.of(plan);
    }

    @Transactional
    public void deletePlan(Member member, Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
        boolean isMemberInPlan = plan.hasMember(member);
        if (!isMemberInPlan) {
            throw new BusinessException(ErrorCode.PLAN_ACCESS_DENIED);
        }
        planRepository.delete(plan);
    }

    @Transactional(readOnly = true)
    public Plan getPlanEntity(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
    }

    @Transactional
    public void inviteMember(Member inviter, Long planId, String inviteeEmail) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAN_NOT_FOUND));
        boolean isMemberOrganized = plan.isOrganizer(inviter);
        if (!isMemberOrganized) {
            throw new BusinessException(ErrorCode.PLAN_ACCESS_DENIED);
        }

        Member invitee = memberRepository.findByEmail(inviteeEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        boolean alreadyExists = plan.hasMember(invitee);
        if (alreadyExists) {
            throw new BusinessException(ErrorCode.MEMBER_ALREADY_IN_PLAN);
        }

        MemberPlan invitation = MemberPlan.createInvitation(invitee, plan);
        memberPlanRepository.save(invitation);
    }

    @Transactional
    public void acceptInvitation(Member accepter, Long invitationId) {
        MemberPlan invitation = memberPlanRepository.findById(invitationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVITATION_NOT_FOUND));
        if (!invitation.getMember().equals(accepter)) {
            throw new BusinessException(ErrorCode.INVITATION_ACCESS_DENIED);
        }

        if (invitation.getStatus() != InvitationStatus.INVITED) {
            throw new BusinessException(ErrorCode.INVITATION_ALREADY_PROCESSED);
        }

        invitation.accept();
    }

    @Transactional(readOnly = true)
    public List<InvitationResponse> getInvitations(Member member) {
        List<MemberPlan> invitations = memberPlanRepository.findByMemberAndStatus(member, InvitationStatus.INVITED);
        return MemberPlanMapper.toInvitationResponse(invitations);
    }
}
