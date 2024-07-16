package com.baggujo.dto;

import com.baggujo.dto.enums.ChatType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Getter
@ToString
public class ChatInsertDTO {
    @NotNull
    private long memberId;
    @NotNull
    private long tradeId;
    private String text;
    private String imgName;
    private String imgSName;
    @NotNull
    private ChatType chatType;
    @NotNull
    private LocalDateTime sendDate;

    public String getFormattedSendDate() {
        if (sendDate == null) {
            return "";
        }
        return sendDate.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
