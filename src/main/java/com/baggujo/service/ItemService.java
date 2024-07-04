package com.baggujo.service;

import com.baggujo.dao.ItemDAO;
import com.baggujo.dao.ItemImageDAO;
import com.baggujo.dto.ItemInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ItemService {

    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private ItemImageDAO itemImageDAO;

    public long insertItem(ItemInsertDTO itemInsertDTO) throws SQLException {
        return itemDAO.insertItem(itemInsertDTO);  //id 값 반환
    }
}
