package com.baggujo.dao;

import com.baggujo.dto.ItemPreviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FavoriteTest {

    @Autowired
    private ItemDAO itemDAO;

//    @Test
//    public void favoriteItemsTest() {
//        List<ItemPreviewDTO> dtos = itemDAO.getFavoriteItemPreviews(1, 2, null);
//
//        for (ItemPreviewDTO dto: dtos) {
//            System.out.println(dto.toString());
//        }
//    }
}
