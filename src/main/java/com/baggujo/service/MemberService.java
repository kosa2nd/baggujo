package com.baggujo.service;

import com.baggujo.dao.MemberDAO;
import com.baggujo.dao.SignupMemberDAO;
import com.baggujo.dto.MemberDTO;
import com.baggujo.dto.SignupMemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MemberService {
    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private SignupMemberDAO signupMemberDAO;

    public MemberDTO findMemberByEmail(String email) throws SQLException, NullPointerException {
        return memberDAO.findMemberByEmail(email);
    }
    public boolean getEmailDuplicated(String email) throws SQLException{
        return memberDAO.getEmailDuplicated(email);
    }
    public void signUpMember(SignupMemberDTO signupMemberDTO) throws SQLException {
        signupMemberDAO.signUpMember(signupMemberDTO);
    }
}
