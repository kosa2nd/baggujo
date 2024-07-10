package com.baggujo.service;

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
}
