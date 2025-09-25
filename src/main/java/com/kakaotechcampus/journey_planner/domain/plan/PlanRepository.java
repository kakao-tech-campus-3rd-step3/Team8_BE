package com.kakaotechcampus.journey_planner.domain.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("SELECT p FROM Plan p WHERE p.member.id=:userId")
    public List<Plan> getAllPlanByUserId(@Param("userId") Long userId);
}
