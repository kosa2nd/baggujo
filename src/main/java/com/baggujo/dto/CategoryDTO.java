package com.baggujo.dto;

import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private long itemCategoryId;
    private String itemCategoryName;

}
