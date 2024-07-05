package com.baggujo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryDTO {

    private long itemCategoryId;
    private String itemCategoryName;

    public long getItemCategoryId() {
        return itemCategoryId;
    }


    public String getItemCategoryName() {
        return itemCategoryName;
    }

}
