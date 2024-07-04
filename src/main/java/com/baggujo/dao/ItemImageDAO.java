package com.baggujo.dao;

import com.baggujo.dto.ItemImageInsertDTO;
import com.baggujo.dto.ItemInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ItemImageDAO {
    int insertItemImages(List<ItemImageInsertDTO> itemInsertDTOList) throws SQLException;
}
