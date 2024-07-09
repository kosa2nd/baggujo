package com.baggujo.controller.rest;

import com.baggujo.dto.AuthDTO;
import com.baggujo.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
public class RestFavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/toggle/{itemId}")
    public ResponseEntity<Map<String, Object>> toggleFavorite(HttpServletRequest request, @PathVariable long itemId, @AuthenticationPrincipal AuthDTO authDTO) {

        if (authDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 로그인되지 않은 경우 false 반환
        }

        try {
            long memberId = authDTO.getId();
            Map<String, Object> result = favoriteService.toggleFavorite(memberId, itemId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count/{itemId}")
    public ResponseEntity<Integer> getTotalFavoriteCount(@PathVariable long itemId) {
        try {
            int totalFavorites = favoriteService.getTotalFavoriteCount(itemId);
            return new ResponseEntity<>(totalFavorites, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
