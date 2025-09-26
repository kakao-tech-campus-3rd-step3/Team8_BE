package com.kakaotechcampus.journey_planner.application.memberplan;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.memberplan.MemberPlan;
import com.kakaotechcampus.journey_planner.domain.memberplan.MemberPlanRepository;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberPlanService {
    private final MemberPlanRepository memberPlanRepository;

    @Transactional
    public MemberPlan createMemberPlan(Member member, Plan plan) {
        MemberPlan memberPlan = MemberPlan.createPlan(member, plan);
        MemberPlan savedMemberPlan = memberPlanRepository.save(memberPlan);
        return savedMemberPlan;
    }
}
