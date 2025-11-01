package com.kakaotechcampus.journey_planner.infra.message;

import com.kakaotechcampus.journey_planner.domain.node.Node;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProvider<T extends Node> {
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, T> hashOperations;

    private final String NODE_HASH_KEY = "node:";

    public void sync(T node){
        String key =  NODE_HASH_KEY + node.getPlanId();
        String field = node.getUuid();
        hashOperations.put(key, field, node);
    }

    public T getNode(T node){
        String key =  NODE_HASH_KEY + node.getPlanId();
        String field = node.getUuid();
        return hashOperations.get(key, field);
    }
}
