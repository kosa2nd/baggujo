package com.baggujo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateTextMemberDTO {

    @NotNull
    private String name;
    @NotNull
    private String nickname;
    @NotNull
    private String phoneNumber;
}
