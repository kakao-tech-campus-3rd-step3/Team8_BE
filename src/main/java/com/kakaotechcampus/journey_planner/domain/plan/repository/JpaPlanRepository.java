package com.kakaotechcampus.journey_planner.domain.plan.repository;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.QPlan;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaPlanRepository {
    private final JPAQueryFactory queryFactory;

    public Slice<Plan> findAllByMemberId(Long memberId, Pageable pageable) {
        QPlan qPlan = QPlan.plan;
        int pageSize = pageable.getPageSize();
        List<Plan> results = queryFactory
                .select(qPlan)
                .from(qPlan)
                .where(qPlan.member.id.eq(memberId))
                .orderBy(qPlan.id.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();
        boolean hasNext = false;

        if (results.size() > pageSize) {
            results.remove(pageSize);
            hasNext = true;
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }
}
