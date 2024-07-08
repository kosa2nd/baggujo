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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/item")
public class RestItemController {
    @Autowired
    private ItemService itemService;
    @Value("${com.baggujo.paging.offset}")
    private int offset;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getItems(@RequestParam(defaultValue = "0") int itemId, @RequestParam(required = false) ItemStatus itemStatus, @RequestParam(defaultValue = "0") int itemCategoryId, @RequestParam(defaultValue = "true") boolean exceptTraded, @RequestParam(required = false) String keyword) {
        Map<String, Object> map = new HashMap<>();
        List<ItemPreviewDTO> itemPreviewDTOS;
        try {
            itemPreviewDTOS = itemService.getItemPreviews(itemId, offset, itemStatus, itemCategoryId, exceptTraded, keyword);
            map.put("items", itemPreviewDTOS);
            long lastItemId = -1;

            if (!itemPreviewDTOS.isEmpty()) {
                lastItemId = itemPreviewDTOS.get(itemPreviewDTOS.size() - 1).getId();
            } else {
                map.put("finished", true);
            }

            map.put("lastItemId", lastItemId);
        } catch (SQLException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
