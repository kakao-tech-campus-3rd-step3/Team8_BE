package com.kakaotechcampus.journey_planner.infra.message;

import com.kakaotechcampus.journey_planner.application.message.MessageService;
import com.kakaotechcampus.journey_planner.domain.node.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamFactory {
    @Value("${redis.channel.stream.group-name}")
    private String groupName;

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisConnectionFactory connectionFactory;

    public <T extends Node> Subscription createSubscription(
            String streamKey,
            Class<T> clazz,
            MessageService<T> service
    ) {
        // * 왜 여기에서 PollTimeout 을 1로 할까에 대해서 의견 조금 더 붙이기.
        var containerOptions = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .keySerializer(StringRedisSerializer.UTF_8)
                .hashKeySerializer(StringRedisSerializer.UTF_8)
                .hashValueSerializer(StringRedisSerializer.UTF_8)
                .targetType(clazz)
                .pollTimeout(Duration.ofSeconds(1))
                .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, T>>  container =
                StreamMessageListenerContainer.create(connectionFactory, containerOptions);

        Subscription subscription = container.receive(
                Consumer.from(groupName, "instance-" + clazz.getSimpleName()),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                (record) -> {
                    T node = record.getValue();
                    service.handle(node);
                }
        );
        container.start();
        return subscription;
    }

    private void createConsumerGroup(String streamKey){
        try{
            redisTemplate.opsForStream().createGroup(streamKey, this.groupName);
        }catch(Exception e){
            if(e.getMessage() != null && e.getMessage().contains("BUSYGROUP")){
                log.warn(e.getMessage());
            }else{
                throw e;
            }
        }
    }
}
