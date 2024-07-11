package com.baggujo.service;

import com.baggujo.dao.RequestDAO;
import com.baggujo.dto.RequestDTO;
import com.baggujo.dto.RequestUserItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    RequestDAO requestDAO;

    // 유저 물품 리스트 조회
    public List<RequestUserItemDTO> getRequestUserItems(long memberId) {
        List<RequestUserItemDTO> requestUserItemDTOs = new ArrayList<>();
        requestUserItemDTOs = requestDAO.getUserItemList(memberId);
        return requestUserItemDTOs;
    }

    // 유저 요청 리스트 조회
    public List<RequestDTO> getRequestList(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException {
        List<RequestDTO> requestDTOs = requestDAO.getRequestByMemberId(memberId, lastRequestId, request, offset);
        System.out.println("Retrieved requests: " + requestDTOs); // 로깅 추가
        return requestDTOs;
    }
}
