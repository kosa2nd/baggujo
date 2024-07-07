package com.baggujo.service;

import com.baggujo.dao.FavoriteDAO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class FavoriteService {

    @Autowired
    public FavoriteDAO favoriteDAO;

    public boolean toggleFavorite(long memberId, long itemId) throws SQLException {
        boolean result;

        if (favoriteDAO.getFavoriteCount(memberId, itemId) > 0) {
            favoriteDAO.deleteFavorite(memberId, itemId);
            result = false;
        } else {
            favoriteDAO.insertFavorite(memberId, itemId);
            result = true;
        }

        return result;
    }

}
