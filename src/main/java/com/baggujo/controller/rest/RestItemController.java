package com.baggujo.controller.rest;

import com.baggujo.dto.*;
import com.baggujo.dao.ItemDAO;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.*;

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

    @GetMapping("/getmyitems")
    public ResponseEntity<Map<String, Object>> getMyItems(@AuthenticationPrincipal AuthDTO authDTO, @RequestParam(required = false, defaultValue = "0") long lastItemId, @RequestParam(required = false) ItemStatus itemStatus, @RequestParam(defaultValue = "12") int offset) {

        if (authDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> map = new HashMap<>();
        List<ItemPreviewDTO> itemPreviewDTOS;
        try {
            itemPreviewDTOS = itemService.getMyItems(authDTO.getId(), lastItemId, itemStatus, offset);
            map.put("items", itemPreviewDTOS);

            long itemId = -1;
            if (!itemPreviewDTOS.isEmpty()) {
                itemId = itemPreviewDTOS.get(itemPreviewDTOS.size() - 1).getId();
            } else {
                map.put("finished", true);
            }

            map.put("lastItemId", itemId);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/deletemyitems")
    public ResponseEntity<String> deleteItem(@RequestParam long memberId, @RequestParam long itemId,
                                             @AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            itemService.deleteItem(memberId, itemId);
            return new ResponseEntity<String>("succeess", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable("id") long id, @Validated @ModelAttribute ItemInsertDTO itemInsertDTO, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            boolean missingValue = false;
            for (FieldError error : bindingResult.getFieldErrors()) {
                sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
                if (Objects.requireNonNull(error.getDefaultMessage()).contains("null")) {
                    missingValue = true;
                }
            }
            if (missingValue) {
                sb.insert(0, "모든 값을 입력해주세요. \n");
            }
            response.put("errorMessage", sb.toString());
            response.put("categories", itemService.getCategories());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            if (itemInsertDTO.getFiles() != null && itemInsertDTO.getFiles().length > 0) {
                boolean hasNonEmptyFile = Arrays.stream(itemInsertDTO.getFiles())
                        .anyMatch(file -> !file.isEmpty());
                if (hasNonEmptyFile) {
                    itemService.updateItem(id, itemInsertDTO);
                } else {
                    itemService.updateItemWithoutImages(id, itemInsertDTO);
                }
            } else {
                itemService.updateItemWithoutImages(id, itemInsertDTO);
            }
            response.put("redirectUrl", "/item/detail/" + id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("errorMessage", "게시글 수정 중 오류가 발생했습니다.");
            response.put("categories", itemService.getCategories());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}