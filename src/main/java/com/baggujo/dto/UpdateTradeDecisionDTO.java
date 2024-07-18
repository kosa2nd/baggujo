package com.baggujo.dto;

import com.baggujo.dto.enums.TradeDecision;
import lombok.Data;

@Data
public class UpdateTradeDecisionDTO {
    private long memberId;
    private long tradeId;
    private TradeDecision tradeDecision;
}
