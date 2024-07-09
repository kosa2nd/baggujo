package com.baggujo.service;

import com.baggujo.dao.RequestDAO;
import com.baggujo.dto.RequestInsertDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class RequestService {

    @Autowired
    RequestDAO requestDAO;

    @Transactional
    public void insertRequest(RequestInsertDTO requestInsertDTO) throws SQLException, BadRequestException {
        //DTO에 들어가 있는 내 물건과 상대 물건의 상태가 거래중이라면 예외를 던진다

        //내 물품 상대 물품 상태(ItemStatus)를 TRADING으로 변경

        //RequestDAO의 insertRequest() 메서드를 활용해 Request 생성
        requestDAO.insertRequest(requestInsertDTO);
    }
}
