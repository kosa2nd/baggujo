package com.baggujo.service;

import com.baggujo.dao.MemberDAO;
import com.baggujo.dto.MemberDTO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MemberService {
    @Autowired
    private MemberDAO memberDAO;

    public MemberDTO findMemberByEmail(String email) throws SQLException, NullPointerException {
        return memberDAO.findMemberByEmail(email);
    }
    public boolean getEmailDuplicated(String email) throws SQLException{
        return memberDAO.getEmailDuplicated(email);
    }
}
