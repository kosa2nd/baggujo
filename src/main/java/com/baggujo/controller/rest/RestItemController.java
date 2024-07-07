package com.baggujo.controller.rest;

import com.baggujo.dto.ItemPreviewDTO;
import com.baggujo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/item")
public class RestItemController {
    @Autowired
    private ItemService itemService;
    @Value("${com.baggujo.paging.offset}")
    private int offset;

    @GetMapping("/list")
    public ResponseEntity<List<ItemPreviewDTO>> getItems(@RequestBody int itemId) {
        List<ItemPreviewDTO> itemPreviewDTOS;
        try {
            itemPreviewDTOS = itemService.getItemPreviews(itemId, offset);
        } catch (SQLException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(itemPreviewDTOS, HttpStatus.OK);
    }

}
