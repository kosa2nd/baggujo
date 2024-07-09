package com.baggujo.dto.enums;

import lombok.Getter;

@Getter
public enum RequestStatus {
    WAITING("응답 대기중"), ACCEPTED("수락"), REJECTED("거절");

    private String kor;

    RequestStatus(String kor) {
        this.kor = kor;
    }

}
