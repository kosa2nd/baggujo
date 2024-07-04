package com.baggujo.dto;

import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Validated
@Getter
public class ItemInsertDTO {

    @NotNull
    private String title;
    @NotNull
    private String description;
//    @NotNull
//    private int enable;
    @NotNull
    private ItemStatus itemStatus;
    @NotNull
    private ItemCondition itemCondition;
    @NotNull
    private long itemCategoryId;
    @NotNull
    private long memberId;

}