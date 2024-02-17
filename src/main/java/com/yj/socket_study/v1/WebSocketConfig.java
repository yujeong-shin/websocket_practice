package com.yj.socket_study.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    // 어떤 요청에 대한 어떤 응답을 할것인지에 대한 정의
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/nanum") // 구현한 웹소켓 핸들러, 웹소켓 연결 주소
//                .setAllowedOrigins("*") //cors 정책으로 인해, 모든 도메인 허용
                .setAllowedOriginPatterns("*")
                .withSockJS(); // WebSocket을 지원하지 않는 구형 브라우저에 대체 옵션을 제공
    }
}
