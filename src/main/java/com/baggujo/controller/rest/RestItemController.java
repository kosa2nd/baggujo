package com.baggujo.controller.rest;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.FavoriteItemPreviewDTO;
import com.baggujo.dto.ItemPreviewDTO;
import com.baggujo.dto.RequestUserItemDTO;
import com.baggujo.dto.enums.ItemStatus;
import com.baggujo.service.ItemService;
import com.baggujo.service.RequestService;
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
import java.util.Objects;

@RestController
@RequestMapping("/item")
public class RestItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private RequestService requestService;
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

    @GetMapping("/getfavorites")
    public ResponseEntity<Map<String, Object>> getFavoriteItems(@RequestParam(defaultValue = "0") int lastFavoriteNo, @RequestParam(required = false) ItemStatus itemStatus, @AuthenticationPrincipal AuthDTO authDTO) {

        if (authDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> map = new HashMap<>();
        List<FavoriteItemPreviewDTO> favoriteItemPreviewDTOs;
        try {
            favoriteItemPreviewDTOs = itemService.getFavoriteItemPreviews(lastFavoriteNo, authDTO.getId(), offset, itemStatus);
            map.put("items", favoriteItemPreviewDTOs);
            long favoriteNo = -1;

            if (!favoriteItemPreviewDTOs.isEmpty()) {
                favoriteNo = favoriteItemPreviewDTOs.get(favoriteItemPreviewDTOs.size() - 1).getFavoriteNo();
            } else {
                map.put("finished", true);
            }

            map.put("lastFavoriteNo", favoriteNo);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/request")
    public ResponseEntity<List<RequestUserItemDTO>> getRequestItems(@AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 로그인되지 않은 경우에 대한 처리
        }

        long memberId = authDTO.getId();
        return new ResponseEntity<>(requestService.getRequestUserItems(memberId), HttpStatus.OK);
    }

}
