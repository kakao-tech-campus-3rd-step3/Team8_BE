package com.kakaotechcampus.journey_planner.domain.traveler;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    List<Traveler> findByMemberAndStatus(Member member, InvitationStatus status);

    List<Traveler> findByPlanAndStatus(Plan plan, InvitationStatus status);
}
