package com.kakaotechcampus.journey_planner.domain.gift.repository;

import com.kakaotechcampus.journey_planner.domain.gift.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    boolean existsByGiftEventIdAndMemberId(Long giftEventId, Long memberId);
}
