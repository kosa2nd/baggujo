package com.baggujo.dao;

import com.baggujo.dto.ItemCategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemCategoryDAO {
    List<ItemCategoryDTO> findItemCategories();
}