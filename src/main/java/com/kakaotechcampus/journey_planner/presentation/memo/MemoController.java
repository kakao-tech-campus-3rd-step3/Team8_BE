package com.kakaotechcampus.journey_planner.presentation.memo;

import com.kakaotechcampus.journey_planner.application.memo.MemoService;
import com.kakaotechcampus.journey_planner.presentation.memo.dto.request.MemoRequest;
import com.kakaotechcampus.journey_planner.presentation.memo.dto.response.MemoResponse;
import com.kakaotechcampus.journey_planner.presentation.utils.MessagingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@MessageMapping("/plans/{planId}/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;
    private final MessagingUtil messagingUtil;
    static final String destination = "memos";

    // 클라이언트가 구독 후 초기 상태 요청 시 전체 memo 전송
    @MessageMapping("/init")
    public void initMemos(@DestinationVariable Long planId) {
        List<MemoResponse> memoResponses = memoService.getMemos(planId);
        messagingUtil.sendResponse(planId, destination, "MEMO_INIT", "memos", memoResponses);
    }

    // 새 memo 생성 후 단건 전송
    @MessageMapping("/create")
    public void createMemo(@DestinationVariable Long planId, @Valid @Payload MemoRequest request) {
        MemoResponse response = memoService.addMemo(planId, request);
        messagingUtil.sendResponse(planId, destination, "MEMO_CREATE", "memo", response);
    }

    // memo 수정 후 단건 전송
    @MessageMapping("/{memoId}/update")
    public void updateMemo(
            @DestinationVariable Long planId,
            @DestinationVariable Long memoId,
            @Valid @Payload MemoRequest request) {

        MemoResponse response = memoService.updateMemo(planId, memoId, request);
        messagingUtil.sendResponse(planId, destination, "MEMO_UPDATE", "memo", response);
    }

    // memo 삭제 후 해당 Id 전송
    @MessageMapping("/{memoId}/delete")
    public void deleteMemo(
            @DestinationVariable Long planId,
            @DestinationVariable Long memoId
    ) {
        memoService.removeMemo(planId, memoId);
        messagingUtil.sendResponse(planId, destination, "MEMO_DELETE", "memoId", memoId);
    }
}