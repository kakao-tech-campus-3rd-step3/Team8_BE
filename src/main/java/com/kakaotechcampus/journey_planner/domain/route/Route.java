package com.kakaotechcampus.journey_planner.domain.route;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "route")
@Getter
@DynamicUpdate
@NoArgsConstructor()
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FromWaypointId", nullable = false)
    private Waypoint fromWayPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ToWaypointId", nullable = false)
    private Waypoint toWayPoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransportationCategory transportationCategory;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Float durationMin;


    public Route(Plan plan,
                 Waypoint fromWayPoint,
                 Waypoint toWayPoint,
                 String title,
                 String description,
                 Float duration,
                 TransportationCategory vehicleCategory) {
        this.plan = plan;
        this.fromWayPoint = fromWayPoint;
        this.toWayPoint = toWayPoint;
        this.title = title;
        this.description = description;
        this.durationMin = duration;
        this.transportationCategory = vehicleCategory;
    }

    public void update(Waypoint from, Waypoint to,
                       String title,
                       String description,
                       Float duration,
                       TransportationCategory vehicleCategory) {
        this.fromWayPoint = from;
        this.toWayPoint = to;
        this.title = title;
        this.description = description;
        this.durationMin = duration;
        this.transportationCategory = vehicleCategory;
    }
}
