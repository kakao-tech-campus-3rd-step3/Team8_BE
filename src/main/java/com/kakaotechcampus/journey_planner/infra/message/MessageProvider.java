package com.kakaotechcampus.journey_planner.infra.message;

import com.kakaotechcampus.journey_planner.application.message.MessageService;
import com.kakaotechcampus.journey_planner.domain.message.MessageBehaviorType;
import com.kakaotechcampus.journey_planner.domain.message.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProvider implements MessageService {

    private static final String MESSAGE_PREFIX = "/topic/plans";
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendInitMessage(MessageType type, Long planId, String destination, Object message) {
        broadcast(type, planId, destination, MessageBehaviorType.INIT, message, null);
    }

    @Override
    public void sendCreateMessage(MessageType type, Long planId, String destination, Object message) {
        broadcast(type, planId, destination, MessageBehaviorType.CREATE, message, null);
    }

    @Override
    public void sendDeleteMessage(MessageType type, Long planId, String destination, Object message) {
        broadcast(type, planId, destination, MessageBehaviorType.DELETE, message, null);
    }

    //  senderSessionId 포함 브로드캐스트
    @Override
    public void sendUpdateMessage(MessageType type, Long planId, String destination, Object message, String senderSessionId) {
        broadcast(type, planId, destination, MessageBehaviorType.UPDATE, message, senderSessionId);
    }

    // 공통화된 브로드캐스트 메서드
    private void broadcast(MessageType type, Long planId, String destination, MessageBehaviorType behaviorType, Object payload, String senderSessionId) {
        String topic = MESSAGE_PREFIX + "/" + planId + "/" + destination;
        simpMessagingTemplate.convertAndSend(topic, getPayload(type, behaviorType, payload, senderSessionId));
    }

    private Map<String, Object> getPayload(MessageType type, MessageBehaviorType behaviorType, Object payload, String senderSessionId) {
        if (senderSessionId != null) {
            return Map.of(
                    "type", behaviorType.name(),
                    "senderSessionId", senderSessionId,
                    type.name(), payload
            );
        }
        return Map.of(
                "type", behaviorType.name(),
                type.name(), payload
        );
    }
}
