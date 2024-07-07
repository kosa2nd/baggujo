package com.baggujo.dao;

import com.baggujo.dto.ItemPreviewDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ItemPreviewTest {

    @Autowired
    private ItemDAO itemDAO;

    @Test
    @DisplayName("상품리스트 조회")
    public void getItemPreviews() throws Exception {
        List<ItemPreviewDTO> item = itemDAO.getItemPreviews(0,12);
        for (ItemPreviewDTO itemPreviewDTO : item) {
            System.out.println("-----------------------");
            System.out.println(itemPreviewDTO.toString());
        }
    }
}
