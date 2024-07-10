package com.baggujo.service;

import com.baggujo.dao.ItemDAO;
import com.baggujo.dao.RequestDAO;
import com.baggujo.dao.TradeDAO;
import com.baggujo.dto.RequestDTO;
import com.baggujo.dto.RequestInsertDTO;
import com.baggujo.dto.TradeInsertDTO;
import com.baggujo.dto.enums.ItemStatus;
import com.baggujo.dto.enums.RequestStatus;
import org.apache.coyote.BadRequestException;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class TradeService {

    @Autowired
    RequestDAO requestDAO;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    TradeDAO tradeDAO;

    public void insertRequest(RequestInsertDTO requestInsertDTO) throws SQLException, BadRequestException {
        if (itemDAO.getItemStatusById(requestInsertDTO.getRequestItemId()) != ItemStatus.WAITING
            || itemDAO.getItemStatusById(requestInsertDTO.getResponseItemId()) != ItemStatus.WAITING) {
            throw new BadRequestException("거래를 요청할 수 없습니다");
        }

        requestDAO.insertRequest(requestInsertDTO);
    }

    public List<RequestDTO> getAvailableRequestsByMemberId(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException {
        return requestDAO.getRequestByMemberId(memberId, lastRequestId, request, offset);
    }

    @Transactional
    public long acceptRequest(long requestId) throws SQLException {
        //물품 상태 변경
        if (itemDAO.updateItemStatusByRequestId(requestId, ItemStatus.TRADING) != 2 ) {
            throw new SQLException("물품 상태 변경에 실패했습니다");
        }

        //요청 상태 변경
        if (requestDAO.updateRequestStatus(requestId, RequestStatus.ACCEPTED) != 1) {
            throw new SQLException("요청 상태 변경에 실패했습니다");
        }

        //거래 생성
        TradeInsertDTO tradeInsertDTO = new TradeInsertDTO(requestId);
        if (tradeDAO.insertTradeByRequestId(tradeInsertDTO) != 1) {
            throw new SQLException("거래 생성에 실패했습니다");
        }

        //거래 아이디 반환
        return tradeInsertDTO.getId();
    }

    public void rejectRequest(long requestId) throws SQLException {
        if (requestDAO.updateRequestStatus(requestId, RequestStatus.REJECTED) != 1) {
            throw new SQLException("요청 상태 변경에 실패했습니다");
        }
    }


}
