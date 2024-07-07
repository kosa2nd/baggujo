package com.baggujo.controller.rest;

import com.baggujo.dto.ItemPreviewDTO;
import com.baggujo.dto.enums.ItemStatus;
import com.baggujo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ItemPreviewDTO>> getItems(@RequestBody int itemId, @RequestParam ItemStatus itemStatus, @RequestParam(defaultValue = "0") int itemCategoryId, @RequestParam(defaultValue = "true") boolean exceptTraded, @RequestParam String keyword) {
        List<ItemPreviewDTO> itemPreviewDTOS;
        try {
            itemPreviewDTOS = itemService.getItemPreviews(itemId, offset, itemStatus, itemCategoryId, exceptTraded, keyword);
        } catch (SQLException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(itemPreviewDTOS, HttpStatus.OK);
    }

}
