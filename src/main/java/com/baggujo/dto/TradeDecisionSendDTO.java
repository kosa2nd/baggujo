package com.baggujo.dto;

import com.baggujo.dto.enums.TradeDecision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TradeDecisionSendDTO {
    private long memberId;
    private long tradeId;
    private TradeDecision tradeDecision;
}
