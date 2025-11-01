package com.kakaotechcampus.journey_planner.infra.message.stomp;

import com.kakaotechcampus.journey_planner.domain.node.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompMessageSender {
    private final static String MESSAGE_PREFIX = "/topic/plans";
    private final SimpMessagingTemplate simpMessagingTemplate;

    public <T extends Node> void sendMessage(T node){
        simpMessagingTemplate.convertAndSend(getDestination(node), getPayload(node));
    }

    private <T extends Node> String getDestination(T node){
        System.out.println(node.getPlanId());
        System.out.println(node.getDestination().getValue());
        return MESSAGE_PREFIX + "/" + node.getPlanId() + "/" + node.getDestination().getValue();
    }

    private <T extends Node> Map<String, Object> getPayload(T node){
        String objectType = node.getDestination().toString();
        return Map.of(
                "type", node.getStatus(),
                objectType, node
        );
    }
}
