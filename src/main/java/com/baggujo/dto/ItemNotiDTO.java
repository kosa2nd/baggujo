package com.baggujo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemNotiDTO {
    private String title;
    private long memberId;
    private String nickname;
}
