package com.baggujo.dao;

import com.baggujo.dto.ItemImageInsertDTO;
import com.baggujo.dto.ItemInsertDTO;
import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class InsertItemImageTest {

    @Autowired
    private ItemImageDAO itemImageDAO;

    @Test
    @DisplayName("상품사진 삽입 테스트")
    public void insertItemImages() throws Exception {
        List<ItemImageInsertDTO> dto = new ArrayList<>();
        dto.add(new ItemImageInsertDTO("사진1","썸네일_사진1",1,1));
        int image = itemImageDAO.insertItemImages(dto);
        System.out.println("테스트: " + image);
    }
}
