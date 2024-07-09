package com.baggujo.dao;

import com.baggujo.dto.enums.ItemStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class updateItemStatusTest {

    @Autowired
    private ItemDAO itemDAO;

    @Test
    @DisplayName("교환 상태 변경 테스트")
    public void insertItemImages() throws Exception {
    itemDAO.updateItemStatus(4, ItemStatus.TRADING);
    }
}
