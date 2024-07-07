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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/favorite")
public class RestFavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @RequestMapping(value = "/toggle/{itemId}")
    public ResponseEntity<Boolean> toggleFavorite(HttpServletRequest request, @PathVariable long itemId, @AuthenticationPrincipal AuthDTO authDTO) {
        try {
            long memberId = authDTO.getId();
            boolean result = favoriteService.toggleFavorite(memberId, itemId);
            return new ResponseEntity<Boolean>(result, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
