package com.kakaotechcampus.journey_planner.infra.message.listener;

import com.kakaotechcampus.journey_planner.application.message.MessageService;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.infra.message.MessageProvider;
import com.kakaotechcampus.journey_planner.infra.message.stomp.StompMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaypointListener implements MessageService<Waypoint> {
    private final StompMessageSender messageSender;
    private final MessageProvider<Waypoint> messageProvider;

    @Override
    public void handle(Waypoint node) {
        messageProvider.sync(node);
        messageSender.sendMessage(node);
    }
}
