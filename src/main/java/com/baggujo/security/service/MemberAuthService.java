package com.baggujo.security.service;

import com.baggujo.dao.MemberDAO;
import com.baggujo.dto.MemberDTO;
import com.baggujo.security.dto.MemberAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MemberAuthService implements UserDetailsService {
    @Autowired
    private MemberDAO memberDAO;

    public MemberAuthDTO loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberDTO memberDTO;

        try {
            memberDTO = memberDAO.findMemberByEmail(email);
            if (memberDTO == null) {
                throw  new UsernameNotFoundException("회원 정보를 찾을 수 없음");
            }
        } catch (SQLException e) {
            throw new UsernameNotFoundException("SQL 문제 발생");
        }

        return new MemberAuthDTO(memberDTO.getId(), memberDTO.getEmail(), memberDTO.getNickname(), memberDTO.getPassword());
    }

}
