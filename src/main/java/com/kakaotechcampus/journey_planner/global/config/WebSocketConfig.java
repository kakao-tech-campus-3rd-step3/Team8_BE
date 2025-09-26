package com.kakaotechcampus.journey_planner.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 순수 WebSocket (k6 같은 부하테스트용)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");

        // 클라이언트가 연결할 WebSocket 엔드포인트
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // 개발 단계에서 모든 origin 허용
                .withSockJS(); // SockJS 설정 (브라우저 호환성 증가)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버 → 클라이언트 메시지 브로커 prefix
        registry.enableSimpleBroker("/topic", "/queue");

        // 클라이언트 → 서버로 보낼 때 prefix
        registry.setApplicationDestinationPrefixes("/app");
    }
}
