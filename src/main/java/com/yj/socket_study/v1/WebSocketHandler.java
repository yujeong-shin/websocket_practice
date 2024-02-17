package com.yj.socket_study.v1;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    // 웹소켓이 연결될 때 생기는 연결 정보를 담고 있는 객체
    // 웹소켓 통신에 대한 처리를 하기 위해 세션들을 컬렉션으로 담아 관리
    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

//    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();


    // 사용자가 웹소켓 서버에 접속하게 되면 동작하는 메소드
    // 커넥션이 맺어질 때 웹소켓 세션 추가 <세션의 고유값, 세션값>
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session);
//        sessions.add(session);
        session.sendMessage(new TextMessage("welcome!!! :)"));
        log.info(session + " 클라이언트 접속");
    }

    // 웹소켓 서버접속이 끝났을때 동작하는 메소드
    // 커넥션이 끊어질 때 웹소켓 세션 삭제
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");
        session.sendMessage(new TextMessage("bye~"));
        CLIENTS.remove(session.getId());
//        sessions.remove(session);
    }

    // 사용자의 메세지를 받게되면 동작하는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = session.getId();  // 메시지를 보낸 아이디
        CLIENTS.entrySet().forEach( arg -> {
            if(!arg.getKey().equals(id)) {  // 같은 아이디가 아니면 메시지를 전달합니다.
                try {
                    arg.getValue().sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    // Arron's code
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
////        JSONObject jsonObject = new JSONObject(payload);
////        for(WebSocketSession s : sessions){
////            s.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + "!"));
////        }
//    }







}