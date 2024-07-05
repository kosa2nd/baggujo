package com.baggujo.dao;

import com.baggujo.dto.ItemImageInsertDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class InsertItemImageTest {

    @Autowired
    private ItemImageDAO itemImageDAO;

    @Test
    @DisplayName("상품사진 삽입 테스트")
    public void insertItemImages() throws Exception {
        List<ItemImageInsertDTO> dto = new ArrayList<>();
        dto.add(new ItemImageInsertDTO("사진1","썸네일_사진1",1,25));
        Map<String, Object> map = new HashMap<>();
        map.put("list", dto);
        int image = itemImageDAO.insertItemImages(map);
        System.out.println("테스트: " + image);
    }
}
