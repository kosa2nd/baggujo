package com.baggujo.service;

import com.baggujo.dao.ItemDAO;
import com.baggujo.dao.RequestDAO;
import com.baggujo.dto.RequestInsertDTO;
import com.baggujo.dto.RequestUserItemDTO;
import com.baggujo.dto.enums.ItemStatus;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    RequestDAO requestDAO;
    @Autowired
    ItemDAO itemDAO;

    @Transactional
    public void insertRequest(RequestInsertDTO requestInsertDTO) throws SQLException, BadRequestException {
        //DTO에 들어가 있는 내 물건과 상대 물건의 상태가 거래중이라면 예외를 던진다
        if (itemDAO.getItemStatusById(requestInsertDTO.getRequestItemId()) != ItemStatus.WAITING
            || itemDAO.getItemStatusById(requestInsertDTO.getResponseItemId()) != ItemStatus.WAITING) {
            throw new BadRequestException("거래를 요청할 수 없습니다");
        }

        //RequestDAO의 insertRequest() 메서드를 활용해 Request 생성
        requestDAO.insertRequest(requestInsertDTO);
    }

    // 유저 물품 리스트 조회
    public List<RequestUserItemDTO> getRequestUserItems(long memberId) {
        List<RequestUserItemDTO> requestUserItemDTOs = new ArrayList<>();
        requestUserItemDTOs = requestDAO.getUserItemList(memberId);
        return requestUserItemDTOs;
    }


}
