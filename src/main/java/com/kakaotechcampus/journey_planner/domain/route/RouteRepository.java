package com.kakaotechcampus.journey_planner.domain.route;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findAllByPlan_Id(Long planId);
    boolean existsByPlan_IdAndFromWayPoint_IdAndToWayPoint_Id(Long planId, Long fromId, Long toId);
}
