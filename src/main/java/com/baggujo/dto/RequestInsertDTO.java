package com.baggujo.dto;

import com.baggujo.dto.enums.ItemStatus;
import com.baggujo.dto.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class RequestInsertDTO {

    private long requestItemId;
    private long responseItemId;
    private RequestStatus requestStatus;

    public RequestInsertDTO(long requestItemId, long responseItemId) {
        this.requestItemId = requestItemId;
        this.responseItemId = responseItemId;
        requestStatus = RequestStatus.WAITING;
    }

}
