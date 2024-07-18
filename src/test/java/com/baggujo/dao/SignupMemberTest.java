package com.baggujo.dao;

import com.baggujo.dto.SignupMemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignupMemberTest {

    @Autowired
    private SignupMemberDAO signupMemberDAO;

    @Test
    public void 회원가입테스트() throws Exception {
        SignupMemberDTO dto = new SignupMemberDTO("zxcv", "zxcv", "코사바꾸조", "바꾸조코사","01022222222");
         signupMemberDAO.signUpMember(dto);
         System.out.println("회원가입 정상");
    }
}
