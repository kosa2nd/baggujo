package com.baggujo.dao;

import com.baggujo.dto.CategoryDTO;
import com.baggujo.dto.ItemDetailDTO;
import com.baggujo.dto.ItemInsertDTO;
import com.baggujo.dto.ItemPreviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ItemDAO {
    long insertItem(ItemInsertDTO itemInsertDTO) throws SQLException;

    ItemDetailDTO getItemDetailById(long id);
    List<CategoryDTO> getCategories();

    List<ItemPreviewDTO> getItemPreviews(int page, int offset) throws SQLException;
}
