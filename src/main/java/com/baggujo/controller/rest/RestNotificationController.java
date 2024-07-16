package com.baggujo.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class RestNotificationController {

    @PostMapping("/removeAllChat")
    public ResponseEntity<boolean> removeAll(@RequestParam long memberId, long tradeId) {
        return new ResponseEntity<boolean>(true, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<boolean> update() {

        return new ResponseEntity<boolean>(true, HttpStatus.OK);
    }


}
