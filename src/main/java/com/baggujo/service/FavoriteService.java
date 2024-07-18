package com.baggujo.service;

import com.baggujo.dao.FavoriteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FavoriteService {

    @Autowired
    public FavoriteDAO favoriteDAO;

    public Map<String, Object> toggleFavorite(long memberId, long itemId) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        boolean isFavorite;

        if (favoriteDAO.getFavoriteCount(memberId, itemId) > 0) {
            favoriteDAO.deleteFavorite(memberId, itemId);
            isFavorite = false;
        } else {
            favoriteDAO.insertFavorite(memberId, itemId);
            isFavorite = true;
        }

        int totalFavorites = favoriteDAO.getTotalFavoriteCount(itemId);
        result.put("isFavorite", isFavorite);
        result.put("totalFavorites", totalFavorites);

        return result;
    }

    public int getTotalFavoriteCount(long itemId) throws SQLException {
        return favoriteDAO.getTotalFavoriteCount(itemId);
    }

    public boolean isFavorite(long memberId, long itemId) throws SQLException {
        return favoriteDAO.getFavoriteCount(memberId, itemId) > 0;
    }

}
