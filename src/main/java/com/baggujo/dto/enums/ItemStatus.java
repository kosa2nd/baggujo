package com.baggujo.dto.enums;

import lombok.Getter;

@Getter
public enum ItemStatus {
    WAITING("미거래"), TRADING("거래중"), TRADED("거래 완료");

    private String kor;

    ItemStatus(String kor) {
        this.kor = kor;
    }
}
