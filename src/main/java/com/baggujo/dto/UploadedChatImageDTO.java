package com.baggujo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UploadedChatImageDTO {
    private String imgName;
    private String imgSName;
}