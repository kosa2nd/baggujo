package com.baggujo.service;

import com.baggujo.dto.TradeDecisionResultDTO;
import com.baggujo.dto.enums.TradeDecision;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class TradeServiceTest {
    @Autowired
    private TradeService tradeService;

    @Test
    public void acceptRequestTest() throws SQLException {
        long tradeIe = tradeService.acceptRequest(41);
        System.out.println("거래 번호 ================" + tradeIe);
    }

    @Test
    public void rejectRequestTest() throws SQLException {
        tradeService.rejectRequest(41);
    }

    @Test
    public void sendTradeDecisionTest() throws Exception {
        long myId = 1L;
        long yourId = 2L;
        long tradeId = 41L;

        TradeDecisionResultDTO dto1 = tradeService.sendDecision(yourId, tradeId, TradeDecision.REJECT);
        TradeDecisionResultDTO dto2 = tradeService.sendDecision(myId, tradeId, TradeDecision.REJECT);
        System.out.println(dto1);
    }
}
