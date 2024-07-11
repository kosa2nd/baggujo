package com.baggujo.controller.rest;

import com.baggujo.dto.ChatInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestChatController {

    @Autowired
    private SimpMessagingTemplate template; // 특정 Broker로 메시지 전달


    // Client가 send할 수 있는 경로
    // stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMappiong 경로가 병합됨
    // "pub/chat/message"
    @MessageMapping("/chat/message")
    public void send(ChatInsertDTO message) {
        template.convertAndSend("/sub/chat/room" + message.getTradeId(), message);
    }

}
