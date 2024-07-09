package com.baggujo.dao;

import com.baggujo.dto.enums.ItemStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Rollback
public class updateItemStatusTest {

    @Autowired
    private ItemDAO itemDAO;

    @Test
    @DisplayName("교환 상태 변경 테스트")
    public void insertItemImages() throws Exception {
        long itemId = 161;
        System.out.println("변경 전 = " + itemDAO.getItemStatusById(itemId).getKor());
        itemDAO.updateItemStatus(itemId, ItemStatus.TRADING);
        System.out.println("변경 후 = " + itemDAO.getItemStatusById(itemId));
    }
}
