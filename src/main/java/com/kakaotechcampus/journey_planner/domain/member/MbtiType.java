package com.kakaotechcampus.journey_planner.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MbtiType {
    INFJ, INFP, INTJ, INTP, ISFJ, ISFP, ISTJ, ISTP, ENFJ, ENFP, ENTJ, ENTP, ESFJ, ESFP, ESTJ, ESTP;

    @JsonCreator
    public static MbtiType from(String s) {
        if (s == null) {
            return null;
        }
        return MbtiType.valueOf(s.toUpperCase());
    }
}
