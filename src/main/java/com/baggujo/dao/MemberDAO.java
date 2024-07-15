package com.baggujo.dao;

import com.baggujo.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface MemberDAO {
    MemberDTO findMemberByEmail(String email) throws SQLException, NullPointerException;
    boolean getEmailDuplicated(String email) throws SQLException;
    long getMemberIdByItemId(long itemId);
}
