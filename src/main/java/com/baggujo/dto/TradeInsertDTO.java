package com.baggujo.dto;

import com.baggujo.dto.enums.TradeDecision;
import com.baggujo.dto.enums.TradeStatus;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class TradeInsertDTO {
    private long id;
    private long requestId;
    private TradeStatus status;
    private TradeDecision tradeDecision;

    public TradeInsertDTO(long requestId) {
        this.requestId = requestId;
        this.status = TradeStatus.TRADING;
        this.tradeDecision = TradeDecision.NONE;
    }
}
