package com.baggujo.service;

import com.baggujo.dao.RequestDAO;
import com.baggujo.dto.RequestUserItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
