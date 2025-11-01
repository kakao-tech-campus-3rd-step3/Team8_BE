package com.kakaotechcampus.journey_planner.domain.waypoint.repository;

import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.global.common.repository.NodeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WaypointRepository extends NodeRepository<Waypoint> {
    List<Waypoint> findAllByPlanId(Long planId);
    @Query("SELECT w FROM Waypoint w WHERE w.id=:waypointId and w.planId=:planId")
    Optional<Waypoint> findByIdAndPlanId(@Param("waypointId") Long waypointId, @Param("planId") Long planId);
}
