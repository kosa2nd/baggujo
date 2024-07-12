package com.baggujo.dao;

import com.baggujo.dto.enums.TradeDecision;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface sendTradeDecisionDAO {
    int sendTradeDecision(long memberId, TradeDecision tradeDecision) throws SQLException;
}
