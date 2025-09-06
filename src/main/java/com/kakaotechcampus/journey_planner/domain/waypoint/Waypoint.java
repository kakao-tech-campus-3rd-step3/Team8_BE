package com.kakaotechcampus.journey_planner.domain.waypoint;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private String name;

    @Embedded
    private Location location;

    private LocalDateTime arriveTime;

    private String locationType;

    private String memo;

    protected Waypoint() {}

    public Waypoint(String name, Location location, LocalDateTime arriveTime, String locationType, String memo) {
        this.name = name;
        this.location = location;
        this.arriveTime = arriveTime;
        this.locationType = locationType;
        this.memo = memo;
    }

    public void assignToPlan(Plan plan) {
        this.plan = plan;
    }

    public void update(String name, Location location, LocalDateTime arriveTime, String locationType, String memo){
        this.name = name;
        this.location = location;
        this.arriveTime = arriveTime;
        this.locationType = locationType;
        this.memo = memo;
    }

}
