package com.baggujo.dto;

import com.baggujo.dto.enums.TradeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TradeDetailDTO {

    private long id;
    private LocalDate tradeStartDate;
    private LocalDate tradeEndDate;

    private long requestMemberId;
    private String requestNickname;
    private long requestItemId;
    private String requestTitle;
    private String requestThumnail;

    private long responseMemberId;
    private String responseNickname;
    private long responseItemId;
    private String responseTitle;
    private String responseThumnail;

    private TradeStatus status;
}
