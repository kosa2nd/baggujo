package com.baggujo.dto;

import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
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
    //테스트 할 때 필요.?
    public ItemPreviewDTO(long id, String s_name, LocalDate uploadDate, ItemStatus status, long itemCategoryId, String category, String member_id, String nickname) {
        this.id = id;
        this.s_name = s_name;
        this.uploadDate = uploadDate;
        this.status = status;
        this.itemCategoryId = itemCategoryId;
        this.category = category;
        this.member_id = member_id;
        this.nickname = nickname;
    }

}
