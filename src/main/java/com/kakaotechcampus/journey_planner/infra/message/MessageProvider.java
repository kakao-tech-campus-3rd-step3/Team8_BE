package com.kakaotechcampus.journey_planner.infra.message;

import com.kakaotechcampus.journey_planner.application.message.MessageService;
import com.kakaotechcampus.journey_planner.domain.message.MessageBehaviorType;
import com.kakaotechcampus.journey_planner.domain.message.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageProvider implements MessageService {
    private final String MESSAGE_PREFIX = "/topic/plans";
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendInitMessage(MessageType type, Long planId, String destination, Object message) {
        simpMessagingTemplate.convertAndSend(
                getDestination(planId, destination),
                getPayload(type, MessageBehaviorType.INIT, message)
        );
    }

    @Override
    public void sendCreateMessage(MessageType type, Long planId, String destination, Object message) {
        simpMessagingTemplate.convertAndSend(
                getDestination(planId, destination),
                getPayload(type, MessageBehaviorType.CREATE, message)
        );
    }

    @Override
    public void sendUpdateMessage(MessageType type, Long planId, String destination, Object message) {
        simpMessagingTemplate.convertAndSend(
                getDestination(planId, destination),
                getPayload(type, MessageBehaviorType.UPDATE, message)
        );
    }

    @Override
    public void sendDeleteMessage(MessageType type, Long planId, String destination, Object message) {
        simpMessagingTemplate.convertAndSend(
                getDestination(planId, destination),
                getPayload(type, MessageBehaviorType.DELETE, message)
        );
    }

    private String getDestination(Long planId, String destination) {
        return MESSAGE_PREFIX + planId + "/" + destination;
    }

    private Map<String, Object> getPayload(MessageType type, MessageBehaviorType behaviorType, Object payload){
        return Map.of(
                "types", type.name(),
                behaviorType.name(), payload
        );
    }
}
