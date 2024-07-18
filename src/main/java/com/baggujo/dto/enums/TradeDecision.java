package com.baggujo.dto.enums;

import lombok.Getter;

@Getter
public enum TradeDecision {
    NONE("요청 없음"), ACCEPT("거래 완료"), REJECT("거래 취소");

    private String kor;

    TradeDecision(String kor) {
        this.kor = kor;
    }


}
