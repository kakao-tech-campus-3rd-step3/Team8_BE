package com.kakaotechcampus.journey_planner.application.message;

import com.kakaotechcampus.journey_planner.domain.node.Node;
import org.springframework.stereotype.Service;

@Service
public interface MessageService<T extends Node> {
    void handle(T node);
}
