package com.baggujo.dto.enums;

import lombok.Getter;

@Getter
public enum TradeStatus {
    TRADING("거래중"), SUCCEED("거래완료"), CANCELED("거래취소");

    private String kor;

    TradeStatus(String kor) {
        this.kor = kor;
    }
}
