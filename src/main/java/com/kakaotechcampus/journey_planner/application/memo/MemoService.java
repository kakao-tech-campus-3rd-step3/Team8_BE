package com.kakaotechcampus.journey_planner.application.memo;

import com.kakaotechcampus.journey_planner.domain.memo.Memo;
import com.kakaotechcampus.journey_planner.domain.memo.MemoRepository;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.domain.plan.PlanRepository;
import com.kakaotechcampus.journey_planner.global.exception.CustomRuntimeException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.dto.memo.MemoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final PlanRepository planRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public void addMemo(Long planId, Memo memo) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PLAN_NOT_FOUND));

        memo.assignToPlan(plan);
        memoRepository.save(memo);
    }

    @Transactional
    public void updateMemo(Long planId, Long memoId, MemoRequest request) {
         Memo memo = memoRepository.findByIdAndPlanId(memoId, planId)
                 .orElseThrow(() -> new CustomRuntimeException(ErrorCode.MEMO_NOT_FOUND));

         memo.update(
             request.title(),
             request.content(),
             request.xPosition(),
             request.yPosition()
         );

    }

    @Transactional
    public void removeMemo(Long planId, Long memoId) {
        Memo memo = memoRepository.findByIdAndPlanId(memoId, planId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.MEMO_NOT_FOUND));

        memoRepository.delete(memo);
    }

    @Transactional(readOnly = true)
    public List<Memo> getMemos(Long planId) {
        return memoRepository.findByPlanId(planId);
    }

}
