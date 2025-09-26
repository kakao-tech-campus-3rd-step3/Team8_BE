package com.kakaotechcampus.journey_planner.domain.memberplan;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_plan")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_plan_id")
    private Long memberPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public MemberPlan(Member member, Plan plan) {
        this.member = member;
        this.plan = plan;
    }
}
