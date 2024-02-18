package com.yj.socket_study.v2;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    // STOMP 웹소켓 통신을 통해 메시지가 들어왔을 때, 메시지의 destination 헤더와 MessageMapping에 설정된 경로가 일치하는 핸들러 찾음
    // prefix로 설정해준 /app와 합쳐져 /app/hello destination 헤더를 가진 메시지들이 이 핸들러를 거침.
    @MessageMapping("/hello")
    // 핸들러에서 처리를 마친 후 반환 값을 /topic/greeting 경로로 메시지를 보냄.
    // 처리를 마친 Greeting 객체를 /topic/greeting 경로로 보냄으로써, 바로 Simple Broker에게 전달.
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}