package com.baggujo.dao;

import com.baggujo.dto.ItemInsertDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemDAO {
    ItemInsertDTO insertItem(ItemInsertDTO item);

}
