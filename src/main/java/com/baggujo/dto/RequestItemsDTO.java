package com.baggujo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestItemsDTO {
    private long requestItemId;
    private long responseItemId;
}
