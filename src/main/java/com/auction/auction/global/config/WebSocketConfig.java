package com.auction.auction.global.config;

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
        // 1. 클라이언트가 웹소켓 연결할 주소: ws://localhost:8080/ws-stomp
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*") // CORS 허용 (프론트랑 주소 다를 때 필수)
                .withSockJS(); // 낮은 버전 브라우저 지원을 위해 설정
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 2. 서버 -> 클라이언트 (구독자에게 메시지 뿌릴 때 쓰는 접두사)
        registry.enableSimpleBroker("/sub");

        // 3. 클라이언트 -> 서버 (메시지 보낼 때 쓰는 접두사)
        registry.setApplicationDestinationPrefixes("/pub");
    }
}