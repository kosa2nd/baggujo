package com.baggujo.dto;

import com.baggujo.dto.enums.RequestStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestInsertDTO {

    private long requestItemId;
    private long responseItemId;
    private RequestStatus requestStatus;

}
