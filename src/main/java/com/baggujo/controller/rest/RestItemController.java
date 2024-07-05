package com.baggujo.controller.rest;

import com.baggujo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestItemController {
    @Autowired
    private ItemService itemService;

}
