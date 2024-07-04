package com.baggujo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@AllArgsConstructor
@Validated
public class ItemImageInsertDTO {
    @NotNull
    private String name;
    @NotNull
    private String sName;
    @NotNull
    private int imgNo;
    @NotNull
    private long itemId;
}
