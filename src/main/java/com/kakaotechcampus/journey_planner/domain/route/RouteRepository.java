package com.kakaotechcampus.journey_planner.domain.route;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findAllByPlan_Id(Long planId);
    Optional<Route> findByIdAndPlanId(Long id, Long planId);
}
