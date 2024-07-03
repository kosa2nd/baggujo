package com.baggujo.service;

import com.baggujo.dto.MemberDTO;
import com.baggujo.security.dto.MemberAuthDTO;
import com.baggujo.security.service.MemberAuthService;
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
    @Autowired
    private MemberAuthService authService;

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

    @Test
    @DisplayName("등록된 회원을 조회해 UserDetails로 변환 테스트")
    public void 변환테스트() throws SQLException{
        String email = "asdf";
        MemberDTO dto = memberService.findMemberByEmail(email);
        MemberAuthDTO auth = authService.loadUserByUsername(dto.getEmail());

        Assertions.assertEquals(dto.getId(), auth.getId());
    }
}
