package com.baggujo.dto;

import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDetailDTO {
    private long id;
    private String title;
    private String description;
    private LocalDate uploadDate;
    private LocalDate updateDate;
    private ItemStatus itemStatus;
    private ItemCondition itemCondition;
    private long itemCategoryId;
    private String itemCategoryName;
    private long member_id;
    private String nickname;
    private List<String> itemImages;
}
