package com.kakaotechcampus.journey_planner.application.memo;

import com.kakaotechcampus.journey_planner.application.plan.PlanService;
import com.kakaotechcampus.journey_planner.domain.memo.Memo;
import com.kakaotechcampus.journey_planner.domain.memo.MemoMapper;
import com.kakaotechcampus.journey_planner.domain.memo.MemoRepository;
import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import com.kakaotechcampus.journey_planner.global.exception.BusinessException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.presentation.memo.dto.request.MemoRequest;
import com.kakaotechcampus.journey_planner.presentation.memo.dto.response.MemoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final PlanService planService;

    @Transactional
    public MemoResponse createMemo(Long planId, MemoRequest request) {
        Memo memo = MemoMapper.toEntity(request);

        Plan plan = planService.getPlanEntity(planId);
        memo.assignToPlan(plan);
        Memo savedMemo = memoRepository.save(memo);

        return MemoMapper.toResponse(savedMemo);
    }

    @Transactional
    public MemoResponse updateMemo(Long planId, Long memoId, MemoRequest request) {
        Memo memo = memoRepository.findByIdAndPlanId(memoId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMO_NOT_FOUND));

        memo.update(
                request.title(),
                request.content(),
                request.xPosition(),
                request.yPosition()
        );

        return MemoMapper.toResponse(memo);
    }

    @Transactional
    public void deleteMemo(Long planId, Long memoId) {
        Memo memo = memoRepository.findByIdAndPlanId(memoId, planId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMO_NOT_FOUND));

        memoRepository.delete(memo);
    }

    public List<MemoResponse> getMemos(Long planId) {
        Plan plan = planService.getPlanEntity(planId);

        List<Memo> memos = memoRepository.findByPlanId(plan.getId());
        return MemoMapper.toResponseList(memos);
    }
}
