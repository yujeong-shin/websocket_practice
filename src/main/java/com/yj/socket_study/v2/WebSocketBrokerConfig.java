package com.yj.socket_study.v2;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 스프링 제공 내장 브로커 사용, prefix가 붙은 메시지 발행 시 브로커가 처리
        // /queue : 메시지가 일대 일로 갈 때, /topic : 메시지가 일대 다로 브로드 캐스팅 될 때 주로 사용
        config.enableSimpleBroker("/topic"); // 여러 개 설정 가능
        // 바로 브로커에게 가는 것이 아니라, 메시지에 어떤 처리나 가공이 필요할 때 메시지 핸들러로 라우팅되는 prefix
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓 연결주소. 처음 웹소켓 핸드쉐이크를 위한 주소
        registry.addEndpoint("/gs-guide-websocket");
//              .withSockJS();  // WebSocket을 지원하지 않는 구형 브라우저에 대체 옵션을 제공
//              .setAllowedOriginPatterns("*") //cors 설정 가능
    }

}