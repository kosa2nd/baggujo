package com.baggujo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
}
