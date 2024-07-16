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

    @Test
    public void 상대아이디번호출력테스트() {
        long otherMemberId = tradeDAO.getOtherMemberId(62, 207); //기댓값 62
        System.out.println("상대 아이디는 === " + otherMemberId);
    }

}
