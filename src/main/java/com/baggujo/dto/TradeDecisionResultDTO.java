package com.baggujo.dto;

import com.baggujo.dto.enums.TradeDecision;
import com.baggujo.dto.enums.TradeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class TradeDecisionResultDTO {
    private TradeDecision requestDecision;
    private TradeDecision responseDecision;
    private TradeStatus tradeStatus;
}
