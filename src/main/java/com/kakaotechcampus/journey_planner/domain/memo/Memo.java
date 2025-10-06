package com.kakaotechcampus.journey_planner.domain.memo;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.route.Route;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Float xPosition;
    private Float yPosition;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waypoint_id")
    private Waypoint waypoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    public Memo(String title, String content, float xPosition, float yPosition) {
        this.title = title;
        this.content = content;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void assignToPlan(Plan plan) {
        this.plan = plan;
    }

    public void assignToWayPoint(Waypoint waypoint) {
        this.waypoint = waypoint;
    }

    public void assignToRoute(Route route) {
        this.route = route;
    }

    public void update(String title, String content, float xPosition, float yPosition) {
        this.title = title;
        this.content = content;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}
