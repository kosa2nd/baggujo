package com.baggujo.controller.rest;

import com.baggujo.dto.ChatInsertDTO;
import com.baggujo.dto.UploadedChatImageDTO;
import com.baggujo.service.ChatService;
import com.baggujo.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@RestController
public class RestChatController {

    @Autowired
    private SimpMessagingTemplate template; // 특정 Broker로 메시지 전달
    @Autowired
    private ChatService chatService;
    @Autowired
    private TradeService tradeService;

    // Client가 send할 수 있는 경로
    // stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMappiong 경로가 병합됨
    @MessageMapping("/chat/message")
    public void send(ChatInsertDTO message) throws SQLException {
        try {
            chatService.insertChat(message);
            template.convertAndSend("/sub/chat/room" + message.getTradeId(), message);
            //전송했으니까 새로운 알림 생성

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/chat/imageUpload")
    public ResponseEntity<UploadedChatImageDTO> itemRejectWithdraw(MultipartFile image) {
        try {
            if (image.isEmpty()) {
                System.out.println("-----------------------업로드 실패" );
            } else {
                System.out.println("-------------------이미지 있음");
            }
            UploadedChatImageDTO uploadChatImageDTO = chatService.getUploadedChatImageNames(image);
            return new ResponseEntity<>(uploadChatImageDTO, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
