package com.baggujo.service;

import com.baggujo.dao.MemberDAO;
import com.baggujo.dao.SignupMemberDAO;
import com.baggujo.dto.MemberDTO;
import com.baggujo.dto.SignupMemberDTO;
import com.baggujo.dto.UpdatePasswordMemberDTO;
import com.baggujo.dto.UpdateTextMemberDTO;
import org.apache.logging.log4j.util.InternalException;
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
    // 비밀번호 수정
    public void updatePasswordMember(UpdatePasswordMemberDTO updatePasswordMemberDTO, long memberId) throws InternalException {
        if (memberDAO.updatePasswordMember(updatePasswordMemberDTO, memberId) != 1) {
            throw new InternalException("비밀번호 수정 실패했습니다.");
        }
    }
    // 그외 내용 수정
    public void updateTextMember(UpdateTextMemberDTO updateTextMemberDTO, long memberId) throws InternalException {
        System.out.println(updateTextMemberDTO + " " + memberId);
        int result = memberDAO.updateTextMember(updateTextMemberDTO, memberId);

        if (result != 1) {
            throw new InternalException("이름, 닉네임, 전화번호 수정 실패했습니다.");
        }
    }
    public UpdateTextMemberDTO getMemberById(long memberId) {
        return memberDAO.getMemberById(memberId);
    }
}
