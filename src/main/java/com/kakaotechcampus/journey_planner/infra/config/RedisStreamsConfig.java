package com.kakaotechcampus.journey_planner.infra.config;

import com.kakaotechcampus.journey_planner.domain.memo.Memo;
import com.kakaotechcampus.journey_planner.domain.waypoint.Waypoint;
import com.kakaotechcampus.journey_planner.infra.message.StreamFactory;
import com.kakaotechcampus.journey_planner.infra.message.listener.MemoListener;
import com.kakaotechcampus.journey_planner.infra.message.listener.WaypointListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.stream.Subscription;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisStreamsConfig {
    private final StreamFactory streamFactory;
    private final WaypointListener waypointListener;
    private final MemoListener memoListener;

    @Bean
    public Subscription waypointSubscription(){
        return streamFactory.createSubscription(Waypoint.class.getSimpleName(), Waypoint.class, waypointListener);
    }

    @Bean
    public Subscription memoSubscription(){
        return streamFactory.createSubscription(Memo.class.getSimpleName(), Memo.class, memoListener);
    }
}
