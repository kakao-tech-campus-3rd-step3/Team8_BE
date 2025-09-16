package com.kakaotechcampus.journey_planner.presentation.memo;

import com.kakaotechcampus.journey_planner.application.memo.MemoService;
import com.kakaotechcampus.journey_planner.domain.memo.Memo;
import com.kakaotechcampus.journey_planner.domain.memo.MemoMapper;
import com.kakaotechcampus.journey_planner.presentation.memo.dto.request.MemoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@MessageMapping("/plans/{planId}/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;
    private final SimpMessagingTemplate messagingTemplate;

    //클라이언트가 구독 후 초기 상태 요청 시 전체 memo 전송
    @MessageMapping("/init")
    public void initMemos(@DestinationVariable Long planId) {
        sendFullMemos(planId);
    }

    // 새 memo 생성 후 전체 리스트 전송
    @MessageMapping("/create")
    public void createMemo(@DestinationVariable Long planId, @Valid @Payload MemoRequest request) {
        Memo memo = MemoMapper.toEntity(request);
        memoService.addMemo(planId, memo);

        sendFullMemos(planId);
    }

    // memo 수정 후 전체 리스트 전송
    @MessageMapping("/{memoId}/update")
    public void updateMemo(
            @DestinationVariable Long planId,
            @DestinationVariable Long memoId,
            @Valid @Payload MemoRequest request) {

        memoService.updateMemo(planId, memoId, request);

        sendFullMemos(planId);
    }

    // memo 삭제 후 전체 리스트 전송
    @MessageMapping("/{memoId}/delete")
    public void deleteMemo(
            @DestinationVariable Long planId,
            @DestinationVariable Long memoId
    ) {
        memoService.removeMemo(planId, memoId);

        sendFullMemos(planId);
    }

    // plan의 전체 memo 리스트를 전송 (Broadcast)
    private void sendFullMemos(Long planId) {
        List<Memo> memos = memoService.getMemos(planId);
        messagingTemplate.convertAndSend(
                "/topic/plans/" + planId + "/memos",
                Map.of(
                        "type", "FULL_UPDATE",
                        "memos", MemoMapper.toResponseList(memos)
                )
        );
    }
}
