package com.baggujo.dto;

import com.baggujo.dto.enums.ChatType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class ChatInsertDTO {
    @NotNull
    private long memberId;
    @NotNull
    private long tradeId;
    @NotNull
    private LocalDate send_date;
    private String text;
    private MultipartFile image;
    @NotNull
    private ChatType chatType;
}
