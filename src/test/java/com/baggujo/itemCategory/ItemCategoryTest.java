package com.baggujo.itemCategory;

import com.baggujo.dao.ItemCategoryDAO;
import com.baggujo.dto.ItemCategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ItemCategoryTest {

    @Autowired
    private ItemCategoryDAO itemCategoryDAO;

    @DisplayName("물품카테고리 테스트")
    @Test
    public void 물품카테고리테스트() {
        List<ItemCategoryDTO> list =  itemCategoryDAO.findItemCategories();

        for (ItemCategoryDTO dto: list) {
            System.out.println(dto.getCategory());
        }
    }

}
