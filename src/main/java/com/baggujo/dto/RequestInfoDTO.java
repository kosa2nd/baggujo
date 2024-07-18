package com.baggujo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestInfoDTO {

    private long requestMemberId;
    private String requestNickname;
    private long requestItemId;
    private String requestTitle;

    private long responseMemberId;
    private String responseNickname;
    private long responseItemId;
    private String responseTitle;
}
