package com.baggujo.dao;

import com.baggujo.dto.RequestDTO;
import com.baggujo.dto.RequestInsertDTO;
import com.baggujo.dto.TradeDetailDTO;
import com.baggujo.dto.enums.RequestStatus;
import com.baggujo.dto.RequestUserItemDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface RequestDAO {

    void insertRequest(RequestInsertDTO requestInsertDTO) throws SQLException;
    List<RequestDTO> getRequestByMemberId(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException;
    List<RequestUserItemDTO> getUserItemList(long memberId);
    int updateRequestStatus(long requestId, RequestStatus requestStatus);
}
