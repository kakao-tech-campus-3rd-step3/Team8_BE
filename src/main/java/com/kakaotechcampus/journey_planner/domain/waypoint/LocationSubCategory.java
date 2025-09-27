package com.kakaotechcampus.journey_planner.domain.waypoint;

public enum LocationSubCategory {

    DEFAULT(LocationCategory.DEFAULT),

    // FOOD
    RESTAURANT(LocationCategory.FOOD),
    CAFE(LocationCategory.FOOD),
    BAR(LocationCategory.FOOD),

    // CULTURE
    MUSEUM(LocationCategory.CULTURE),
    LIBRARY(LocationCategory.CULTURE),
    CENTER(LocationCategory.CULTURE),

    // ACCOMMODATION
    HOTEL(LocationCategory.ACCOMMODATION),

    // TOUR
    STORE(LocationCategory.TOUR),
    LANDMARK(LocationCategory.TOUR),
    ACTIVITY(LocationCategory.TOUR),

    // TRANSPORTATION
    AIRPORT(LocationCategory.TRANSPORTATION),
    TERMINAL(LocationCategory.TRANSPORTATION),
    STATION(LocationCategory.TRANSPORTATION);

    private final LocationCategory parentCategory;

    LocationSubCategory(LocationCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public LocationCategory getParentCategory() {
        return parentCategory;
    }
}
