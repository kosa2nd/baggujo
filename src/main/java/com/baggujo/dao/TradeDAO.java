package com.baggujo.dao;

import com.baggujo.dto.TradeDetailDTO;
import com.baggujo.dto.TradeInfoDTO;
import com.baggujo.dto.TradeInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface TradeDAO {

    int insertTradeByRequestId(TradeInsertDTO tradeInsertDTO);
    TradeInfoDTO getTradeDetailByTradeId(long tradeId);
    List<TradeDetailDTO> getTradeListByMemberId(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException;
    List<TradeDetailDTO> getTradeSucceedListByMemberId(long memberId, long lastRequestId, Boolean request, long offset);
}
