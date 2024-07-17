package com.baggujo.dao;

import com.baggujo.dto.MemberDTO;
import com.baggujo.dto.UpdatePasswordMemberDTO;
import com.baggujo.dto.UpdateTextMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface MemberDAO {
    MemberDTO findMemberByEmail(String email) throws SQLException, NullPointerException;
    boolean getEmailDuplicated(String email) throws SQLException;
    int updatePasswordMember(UpdatePasswordMemberDTO updatePasswordMemberDTO, long memberId);   // 비밀번호 수정
    int updateTextMember(UpdateTextMemberDTO updateTextMemberDTO, long memberId);   // 그외 내용 수정
    UpdateTextMemberDTO getMemberById(long memberId);
    long getMemberIdByItemId(long itemId);
}
