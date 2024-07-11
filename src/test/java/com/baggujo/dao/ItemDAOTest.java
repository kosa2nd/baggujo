package com.baggujo.dao;

import com.baggujo.dto.FavoriteItemPreviewDTO;
import com.baggujo.dto.ItemInsertDTO;
import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import com.baggujo.dto.enums.RequestStatus;
import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    @Test
    @DisplayName("관심 물품 조회 테스트")
    public void 관심물품조회테스트() throws SQLException {
        List<FavoriteItemPreviewDTO> dtos = itemdao.getFavoriteItemPreviews(0, 1, 30, null);
        for (FavoriteItemPreviewDTO dto: dtos) {
            System.out.println(dto);
        }
        System.out.println("총 개수 = " + dtos.size());
        //long lastFavoriteNo, long memberId, int offset, ItemStatus itemStatus
    }
}
