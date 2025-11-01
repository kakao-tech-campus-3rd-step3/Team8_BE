package com.kakaotechcampus.journey_planner.infra.message.listener;

import com.kakaotechcampus.journey_planner.application.message.MessageService;
import com.kakaotechcampus.journey_planner.domain.route.Route;
import com.kakaotechcampus.journey_planner.infra.message.MessageProvider;
import com.kakaotechcampus.journey_planner.infra.message.stomp.StompMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteListener implements MessageService<Route> {
    private final StompMessageSender messageSender;
    private final MessageProvider<Route> messageProvider;

    @Override
    public void handle(Route node) {
        messageProvider.sync(node);
        messageSender.sendMessage(node);
    }
}
