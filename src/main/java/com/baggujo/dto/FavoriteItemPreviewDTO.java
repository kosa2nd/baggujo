package com.baggujo.dto;

import com.baggujo.dto.enums.ItemStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class FavoriteItemPreviewDTO extends ItemPreviewDTO {

    private long favoriteNo;

    public FavoriteItemPreviewDTO(long id, String title, String s_name, LocalDate uploadDate, ItemStatus status, long itemCategoryId, String category, String member_id, String nickname, long favoriteNo) {
        super(id, title, s_name, uploadDate, status, itemCategoryId, category, member_id, nickname);
        this.favoriteNo = favoriteNo;
    }

    @Override
    public String toString() {
        return super.toString() + ", favoriteNo: " + favoriteNo ;
    }
}
