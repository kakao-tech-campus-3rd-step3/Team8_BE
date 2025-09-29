package com.kakaotechcampus.journey_planner.application.memberplan;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.traveler.Traveler;
import com.kakaotechcampus.journey_planner.domain.traveler.TravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TravelerService {
    private final TravelerRepository travelerRepository;

    @Transactional
    public Traveler createTraveler(Member member, Plan plan) {
        Traveler traveler = Traveler.createPlan(member, plan);
        return travelerRepository.save(traveler);
    }
}
