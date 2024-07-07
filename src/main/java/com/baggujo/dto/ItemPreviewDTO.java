package com.baggujo.dto;

import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class ItemPreviewDTO {

    private long id;
    private String title;
    private String s_name;
    private LocalDate uploadDate;
    private ItemStatus status;
    private long itemCategoryId;
    private String category;
    private String member_id;
    private String nickname;

}
