package com.baggujo.dao;

import com.baggujo.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
    MemberDTO findMemberByEmail(String email);
}
