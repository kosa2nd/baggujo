package com.baggujo.dto;

import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDetailDTO {
    private long id;
    private String title;
    private String description;
    private ItemStatus itemStatus;
    private ItemCondition itemCondition;
    private long itemCategoryId;
    private String itemCategoryName;
}
