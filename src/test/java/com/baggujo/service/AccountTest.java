package com.baggujo.service;

import com.baggujo.dto.MemberDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.sql.SQLException;

@SpringBootTest
@Rollback
public class AccountTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("등록된 회원을 조회 테스트")
    public void loginTest() throws SQLException, NullPointerException {
        String email = "asdf";
        MemberDTO dto = memberService.findMemberByEmail(email);

        Assertions.assertEquals(dto.getEmail(), email);
    }

    @Test
    @DisplayName("등록되지 않은 회원을 대상으로 조회시 오류 발생 테스트")
    public void wrongEmailTest() throws SQLException{
        String email = "89uqwru8ru8op";
        Assertions.assertNull(memberService.findMemberByEmail(email));
    }
}
