package com.baggujo.dao;

import com.baggujo.dto.SignupMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface SignupMemberDAO {
    void signUpMember(SignupMemberDTO signupMemberDTO) throws SQLException;
}
