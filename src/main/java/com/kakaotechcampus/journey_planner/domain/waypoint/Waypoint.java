package com.kakaotechcampus.journey_planner.domain.waypoint;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationSubCategory locationSubCategory;

    private Float xPosition;
    private Float yPosition;


    public Waypoint(String name, String description, String address, LocalDateTime startTime, LocalDateTime endTime, LocationCategory locationCategory, LocationSubCategory locationSubCategory,Float xPosition, Float yPosition) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.locationCategory = locationCategory;
        this.locationSubCategory = locationSubCategory;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void assignToPlan(Plan plan) {
        this.plan = plan;
    }

    public void update(String name, String description, String address, LocalDateTime startTime, LocalDateTime endTime, LocationCategory locationCategory, LocationSubCategory locationSubCategory, Float xPosition, Float yPosition) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.locationCategory = locationCategory;
        this.locationSubCategory = locationSubCategory;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void validateCategory(LocationCategory category, LocationSubCategory subCategory) {
        if (subCategory.getParentCategory() != category) {
            throw new BusinessException(ErrorCode.WAYPOINT_NOT_FOUND);
        }
    }

}
