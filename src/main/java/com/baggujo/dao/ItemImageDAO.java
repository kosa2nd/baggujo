package com.baggujo.dao;

import com.baggujo.dto.ItemImageInsertDTO;
import com.baggujo.dto.ItemInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface ItemImageDAO {
    int insertItemImages(Map<String, Object>map) throws SQLException;
    int deleteItemImages(long id);
}
