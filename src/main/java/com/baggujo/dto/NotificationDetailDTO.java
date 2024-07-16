package com.baggujo.dto;

import com.baggujo.dto.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NotificationDetailDTO {
    private long id;
    private LocalDate notifiedDate;
    private NotificationStatus status;
    private String text;
    private String link;
    private boolean isChat;
    private Long tradeId;
}
