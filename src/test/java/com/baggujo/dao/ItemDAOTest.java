package com.baggujo.dao;

import com.baggujo.dto.ItemInsertDTO;
import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import com.baggujo.dto.enums.RequestStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest
public class ItemDAOTest {

    @Autowired
    private ItemDAO itemdao;

    @Test
    @DisplayName("상품글 삽입 테스트")
    public void insertItem() throws Exception {
        ItemInsertDTO dto = new ItemInsertDTO("키보드","설명", ItemStatus.WAITING, ItemCondition.BEST, 1, 1);
        long id = itemdao.insertItem(dto);
        System.out.println("----" + id);
    }

    @Test
    @DisplayName("요청으로 거래 물품 상태 변경 테스트")
    public void updateItemStatusByRequestIDTest() {
        itemdao.updateItemStatusByRequestId(41, ItemStatus.TRADING);
    }
}
