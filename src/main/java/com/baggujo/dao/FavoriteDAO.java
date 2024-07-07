package com.baggujo.dao;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface FavoriteDAO {
    int getFavoriteCount(long memberId, long itemId) throws SQLException;
    void insertFavorite(long memberId, long itemId) throws SQLException;
    void deleteFavorite(long memberId, long itemId) throws SQLException;
}
