package com.baggujo.dto;

import com.baggujo.dto.enums.ChatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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
}
