package com.baggujo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class FavoriteServiceTest {
    @Autowired
    private FavoriteService favoriteService;

    @Test
    @DisplayName("즐겨찾기 토글 테스트")
    public void 즐겨찾기토글테스트() throws SQLException {
        long memberId = 1L;
        long itemId = 91L;

        boolean result1 = favoriteService.toggleFavorite(memberId, itemId);
        boolean result2 = favoriteService.toggleFavorite(memberId, itemId);
        Assertions.assertTrue(result1 == !result2);
    }

}
