package com.baggujo.dto;

import com.baggujo.dto.enums.ChatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Getter
public class ChatInsertDTO {
    @NotNull
    private long memberId;
    @NotNull
    private long tradeId;
    @NotNull
    private LocalDate send_date;
    private String text;
    @Null
    private MultipartFile image;
    @NotNull
    private ChatType chatType;
}
