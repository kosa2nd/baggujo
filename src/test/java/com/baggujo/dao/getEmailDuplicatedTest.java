package com.baggujo.dao;

import com.baggujo.dto.MemberDTO;
import com.baggujo.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class getEmailDuplicatedTest {

    @Autowired
    private MemberDAO memberDAO;

    @Test
    public void 중복이메일조회테스트() throws SQLException {
        String email = "asdf";
        memberDAO.getEmailDuplicated(email);
        boolean isDuplicated = memberDAO.getEmailDuplicated(email);
        boolean expectedValue = true;

        Assertions.assertEquals(expectedValue, isDuplicated);
    }
}
