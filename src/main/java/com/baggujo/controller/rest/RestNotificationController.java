package com.baggujo.controller.rest;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.NotificationDetailDTO;
import com.baggujo.dto.enums.NotificationStatus;
import com.baggujo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class RestNotificationController {

    @Autowired
    NotificationService notificationService;
    @Value("${com.baggujo.paging.offset}")
    private int offset;

    //채팅방 입장하거나 채팅 수신시 관련 채팅 알림 모두 지우기
    @PostMapping("/removeAllChat")
    public ResponseEntity<Boolean> removeAll(@RequestParam long memberId, @RequestParam long tradeId, @AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            notificationService.removeAllChat(memberId, tradeId);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //조회
    @GetMapping("/list")
    public ResponseEntity<List<NotificationDetailDTO>> getNotifications(@AuthenticationPrincipal AuthDTO authDTO, @RequestParam long memberId) {
        if (authDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<NotificationDetailDTO> dto = notificationService.getNotifications(memberId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //알림
    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestParam long notificationId, @RequestParam NotificationStatus notificationStatus) {
        notificationService.updateNotification(notificationId, notificationStatus);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
