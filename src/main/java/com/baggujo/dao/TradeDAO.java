package com.baggujo.dao;

import com.baggujo.dto.TradeInsertDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeDAO {

    int insertTradeByRequestId(TradeInsertDTO tradeInsertDTO);

}
