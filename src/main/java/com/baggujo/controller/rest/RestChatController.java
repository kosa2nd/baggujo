package com.baggujo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestChatController {

    @Autowired
    private SimpMessagingTemplate template;

    private void send() {

    }

}
