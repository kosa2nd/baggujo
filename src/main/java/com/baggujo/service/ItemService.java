package com.baggujo.service;

import com.baggujo.dao.ItemDAO;
import com.baggujo.dao.ItemImageDAO;
import com.baggujo.dto.ItemInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class ItemService {

    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private ItemImageDAO itemImageDAO;

    @Transactional
    public long insertItem(ItemInsertDTO itemInsertDTO) throws SQLException {
        long id = itemDAO.insertItem(itemInsertDTO);  //id 값 반환

        //이미지 저장 쿼리

        return id;
    }
}
