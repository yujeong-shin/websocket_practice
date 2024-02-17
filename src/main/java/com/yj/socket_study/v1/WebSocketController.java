package com.yj.socket_study.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class WebSocketController {
    @GetMapping("/socket-test")
    public String chatGET(){
        log.info("@WebSocketController, GET()");
        return "SharingRoomDetail";
    }
}
