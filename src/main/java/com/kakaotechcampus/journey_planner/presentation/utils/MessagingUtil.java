package com.kakaotechcampus.journey_planner.presentation.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessagingUtil {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendResponse(Long planId, String destination, String type, String payloadType, Object payload) {
        messagingTemplate.convertAndSend(
                "/topic/plans/" + planId + "/" + destination,
                Map.of(
                        "type", type,
                        payloadType, payload
                )
        );
    }
}
