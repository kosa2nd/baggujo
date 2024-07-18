package com.baggujo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class RequestDTO {
    private long id;
    private LocalDate requestDate;

    private long requestMemberId;
    private String requestNickname;
    private long requestItemId;
    private String requestItemTitle;
    private String requestThumbnail;

    private long responseMemberId;
    private String responseNickname;
    private long responseItemId;
    private String responseItemTitle;
    private String responseThumbnail;
}
