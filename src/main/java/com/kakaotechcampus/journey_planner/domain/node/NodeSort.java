package com.kakaotechcampus.journey_planner.domain.node;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeSort {
    WAYPOINT("waypoints"),
    MEMO("memos"),
    ROUTE("routes")
    ;

    private final String value;
}
