package com.baggujo.dto.enums;

import lombok.Getter;

@Getter
public enum ItemCondition {

    PACKED("미개봉"), BEST("최상"), BETTER("사용감 있음"), GOOD("사용감 많음"), BAD("상태 안좋음");

    private final String kor;

    ItemCondition(String kor) {
        this.kor = kor;
    }

}
