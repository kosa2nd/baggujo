package com.baggujo.dao;

import com.baggujo.dto.ItemInsertDTO;
import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import com.baggujo.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InsertItemTest {

    @Autowired
    private ItemDAO itemdao;

    @Test
    @DisplayName("상품글 삽입 테스트")
    public void insertItem() throws Exception {
        ItemInsertDTO dto = new ItemInsertDTO("키보드","설명",1, ItemStatus.미거래, ItemCondition.미개봉, 2, 2);
        Long id  = itemdao.insertItem(dto);
        System.out.println("----" + id);
        Assertions.assertNotNull(id);
    }
}
