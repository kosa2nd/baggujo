package com.baggujo.dto;

import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.dto.enums.ItemStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
@Data
public class ItemInsertDTO {
    private long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private ItemStatus itemStatus;
    @NotNull
    private ItemCondition itemCondition;
    @NotNull
    @Positive
    private long itemCategoryId;
    @NotNull
    private long memberId;
    private MultipartFile[] multipartFiles;

    public ItemInsertDTO(String title, String description, ItemStatus itemStatus, ItemCondition itemCondition, long itemCategoryId, long memberId) {
        this.title = title;
        this.description = description;
        this.itemStatus = itemStatus;
        this.itemCondition = itemCondition;
        this.itemCategoryId = itemCategoryId;
        this.memberId = memberId;
    }

}