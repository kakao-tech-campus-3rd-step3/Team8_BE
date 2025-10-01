package com.kakaotechcampus.journey_planner.domain.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("SELECT t.plan FROM Traveler t WHERE t.member.id = :memberId AND t.status = 'ACCEPTED'")
    List<Plan> findAllByMemberId(@Param("memberId") Long memberId);
}
