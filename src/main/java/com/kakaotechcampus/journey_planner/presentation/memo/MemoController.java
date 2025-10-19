package com.kakaotechcampus.journey_planner.presentation.memo;

import com.kakaotechcampus.journey_planner.application.memo.MemoService;
import com.kakaotechcampus.journey_planner.application.message.MessageService;
import com.kakaotechcampus.journey_planner.presentation.memo.dto.request.MemoRequest;
import com.kakaotechcampus.journey_planner.presentation.memo.dto.response.MemoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.kakaotechcampus.journey_planner.domain.message.MessageType.MEMO;

@Slf4j
@Controller
@RequiredArgsConstructor
@MessageMapping("/plans/{planId}/memos")
public class MemoController {

    private final MemoService memoService;
    private final MessageService messageService;
    private static final String DESTINATION = "memos";

    // 초기화: 전체 메모 목록 전송
    @MessageMapping("/init")
    public void initMemos(@DestinationVariable Long planId) {
        List<MemoResponse> memoResponses = memoService.getMemos(planId);
        messageService.sendInitMessage(MEMO, planId, DESTINATION, memoResponses);
    }


    @MessageMapping("/create")
    public void createMemo(
            @DestinationVariable Long planId,
            @Valid @Payload MemoRequest request,
            @Header("simpSessionId") String sessionId
    ) {
        MemoResponse response = memoService.createMemo(planId, request);
        messageService.sendCreateMessage(MEMO, planId, DESTINATION, response);
    }

    // (자기 세션 제외 브로드캐스트)
    @MessageMapping("/{memoId}/update")
    public void updateMemo(
            @DestinationVariable Long planId,
            @DestinationVariable Long memoId,
            @Valid @Payload MemoRequest request,
            @Header("simpSessionId") String sessionId
    ) {
        MemoResponse response = memoService.updateMemo(planId, memoId, request);
        messageService.sendUpdateMessage(MEMO, planId, DESTINATION, response, sessionId);
    }

    @MessageMapping("/{memoId}/delete")
    public void deleteMemo(
            @DestinationVariable Long planId,
            @DestinationVariable Long memoId
    ) {
        memoService.deleteMemo(planId, memoId);
        messageService.sendDeleteMessage(MEMO, planId, DESTINATION, memoId);
    }
}
