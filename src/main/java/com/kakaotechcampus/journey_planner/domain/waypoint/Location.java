package com.kakaotechcampus.journey_planner.domain.waypoint;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable // 다른 엔티티에 삽입 가능
public class Location {

    private String address;

    private Double latitude;

    private Double longitude;

    protected Location() {}

    public Location(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}