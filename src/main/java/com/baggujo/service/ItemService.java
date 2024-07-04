package com.baggujo.service;

import com.baggujo.dao.ItemDAO;
import com.baggujo.dao.ItemImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private ItemImageDAO itemImageDAO;
}
