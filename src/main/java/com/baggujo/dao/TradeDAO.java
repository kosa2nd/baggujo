package com.baggujo.dao;

import com.baggujo.dto.TradeDecisionResultDTO;
import com.baggujo.dto.TradeDetailDTO;
import com.baggujo.dto.TradeInfoDTO;
import com.baggujo.dto.TradeInsertDTO;
import com.baggujo.dto.enums.TradeDecision;
import com.baggujo.dto.enums.TradeStatus;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface TradeDAO {

    int insertTradeByRequestId(TradeInsertDTO tradeInsertDTO);
    TradeInfoDTO getTradeInfoByTradeId(long tradeId);
    List<TradeDetailDTO> getTradeListByMemberId(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException;
    List<TradeDetailDTO> getTradeSucceedListByMemberId(long memberId, long lastRequestId, Boolean request, long offset);

    int updateRequestDecision(long tradeId, TradeDecision tradeDecision);
    int updateResponseDecision(long tradeId, TradeDecision tradeDecision);
    TradeDecisionResultDTO getTradeDecisionResultByTradeId(long tradeId);

    int updateTradeStatusByTradeId(long tradeId, TradeStatus tradeStatus);

    long getOtherMemberId(long memberId, long tradeId);
}
