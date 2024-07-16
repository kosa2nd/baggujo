package com.baggujo.controller.rest;

import com.baggujo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class RestNotificationController {

    @Autowired
    NotificationService notificationService;
    @Value("${com.baggujo.paging.offset}")
    private int offset;

    //채팅방 입장하거나 채팅 수신시 관련 채팅 알림 모두 지우기
    @PostMapping("/removeAllChat")
    public ResponseEntity<Boolean> removeAll(@RequestParam long memberId, @RequestParam long tradeId) {
        try {
            notificationService.removeAllChat(memberId, tradeId);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //알림
//    @PostMapping("/update")
//    public ResponseEntity<boolean> update() {
//
//            long notificationId = -1;
//            if (!dtos.isEmpty()) {
//                notificationId = dtos.get(dtos.size() - 1).getId();
//            } else {
//                map.put("finished", true);
//            }
//
//            map.put("lastNotificationId", notificationId);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }

}
