package com.kakaotechcampus.journey_planner.application.memberplan;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.traveler.InvitationStatus;
import com.kakaotechcampus.journey_planner.domain.traveler.Traveler;
import com.kakaotechcampus.journey_planner.domain.traveler.TravelerMapper;
import com.kakaotechcampus.journey_planner.domain.traveler.TravelerRepository;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.traveler.dto.response.TravelerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelerService {
    private final TravelerRepository travelerRepository;

    @Transactional
    public Traveler createTraveler(Member member, Plan plan) {
        Traveler traveler = Traveler.createPlan(member, plan);
        return travelerRepository.save(traveler);
    }

    @Transactional(readOnly = true)
    public List<TravelerResponse> getTravelers(Plan plan) {
        List<Traveler> travelers = travelerRepository.findByPlanAndStatus(plan, InvitationStatus.ACCEPTED);
        return TravelerMapper.toResponseList(travelers);
    }

    @Transactional
    public void deleteTraveler(Long travelerId) {
        Traveler traveler  = travelerRepository.findById(travelerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TRAVELER_NOT_FOUND));

        travelerRepository.delete(traveler);
    }
}
