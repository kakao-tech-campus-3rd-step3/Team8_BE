package com.kakaotechcampus.journey_planner.domain.waypoint;

import com.kakaotechcampus.journey_planner.domain.node.Node;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.route.Route;
import com.kakaotechcampus.journey_planner.presentation.waypoint.dto.request.WayPointModifyDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.kakaotechcampus.journey_planner.domain.node.NodeSort.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Waypoint extends Node {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    private String name;

    private String description;

    private String address;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationCategory locationCategory;

    private Float xPosition;
    private Float yPosition;

    // fromWayPoint로 연결된 Route들
    @Transient
    @OneToMany(mappedBy = "fromWayPoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Route> routesFrom = new ArrayList<>();

    // toWayPoint로 연결된 Route들
    @Transient
    @OneToMany(mappedBy = "toWayPoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Route> routesTo = new ArrayList<>();

    public Waypoint(
            String name,
            String description,
            String address,
            LocalDateTime startTime,
            LocalDateTime endTime,
            LocationCategory locationCategory,
            Float xPosition,
            Float yPosition
    ) {
        super.setDestination(WAYPOINT);
        this.name = name;
        this.description = description;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.locationCategory = locationCategory;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void assignToPlan(Plan plan) {
        super.setPlanId(plan.getId());
        this.plan = plan;
    }

    public void update(WayPointModifyDto request) {
        if(request.name() != null && !request.name().isEmpty()) {
            this.name = request.name();
        }
        if(request.description() != null && !request.description().isEmpty()) {
            this.description = request.description();
        }
        if(request.address() != null && !request.address().isEmpty()) {
            this.address = request.address();
        }
        if(request.startTime() != null) {
            this.startTime = request.startTime();
        }
        if(request.endTime() != null) {
            this.endTime = request.endTime();
        }
        if(request.locationCategory() != null) {
            this.locationCategory = request.locationCategory();
        }
        if(request.xPosition() != null) {
            this.xPosition = request.xPosition();
        }
        if(request.yPosition() != null) {
            this.yPosition = request.yPosition();
        }
    }
}
