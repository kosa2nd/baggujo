package com.baggujo.dao;

import com.baggujo.dto.TradeInsertDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeDAOTest {

    @Autowired
    TradeDAO tradeDAO;

    @Test
    @DisplayName("거래 생성 테스트")
    public void 거래생성테스트() {
        long tradeId = tradeDAO.insertTradeByRequestId(new TradeInsertDTO(41));
        System.out.println("결과 = " + tradeId);
    }

}
