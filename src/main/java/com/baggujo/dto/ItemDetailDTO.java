package com.baggujo.dto;

import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDetailDTO {
    private long id;
    private String title;
    private String description;
    private LocalDateTime uploadDate;
    private LocalDateTime updateDate;
    private ItemStatus itemStatus;
    private ItemCondition itemCondition;
    private long itemCategoryId;
    private String itemCategoryName;
    private long memberId;
    private String nickname;
    private List<String> itemImages;
    private int enable;

    public String getFormattedUploadDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return uploadDate.format(formatter);
    }

    public String getFormattedUpdateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updateDate.format(formatter);
    }
}
