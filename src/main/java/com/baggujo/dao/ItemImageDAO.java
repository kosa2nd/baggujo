package com.baggujo.dao;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface ItemImageDAO {
    int insertItemImages(Map<String, Object>map) throws SQLException;
    int deleteItemImages(long id);
}
