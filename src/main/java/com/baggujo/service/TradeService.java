package com.baggujo.service;

import com.baggujo.dao.*;
import com.baggujo.dto.*;
import com.baggujo.dto.enums.ItemStatus;
import com.baggujo.dto.enums.RequestStatus;
import com.baggujo.dto.enums.TradeDecision;
import com.baggujo.dto.enums.TradeStatus;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.InternalException;
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
    @Autowired
    NotificationDAO notificationDAO;
    @Autowired
    MemberDAO memberDAO;

    // 요청 생성
    @Transactional
    public void insertRequest(RequestInsertDTO requestInsertDTO) throws SQLException, BadRequestException {
        if (itemDAO.getItemStatusById(requestInsertDTO.getRequestItemId()) != ItemStatus.WAITING
            || itemDAO.getItemStatusById(requestInsertDTO.getResponseItemId()) != ItemStatus.WAITING) {
            throw new BadRequestException("거래를 요청할 수 없습니다");
        }

        //상대방에게 알림 생성
        ItemNotiDTO itemNotiDTO = itemDAO.getItemNoti(requestInsertDTO.getResponseItemId());
        notificationDAO.insertNotification(new NotificationInsertDTO(itemNotiDTO.getMemberId(),
                "'" + itemNotiDTO.getTitle() + "'에 대한 거래 요청이 있어요","/trade/myTrade"));

        requestDAO.insertRequest(requestInsertDTO);
    }

    //유저 물품 리스트 조회
    public List<RequestDTO> getAvailableRequestsByMemberId(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException {
        return requestDAO.getRequestByMemberId(memberId, lastRequestId, request, offset);
    }

    //요청 수락
    @Transactional
    public long acceptRequest(long requestId) throws SQLException {
        //물품 상태 변경
        if (itemDAO.updateItemStatusByRequestId(requestId, ItemStatus.TRADING) != 2) {
            throw new SQLException();
        }

        //해당 요청의 물품들과 관련된 다른 요청들에 대해 취소되었다는 알림 생성
        notificationDAO.insertRequestCancelNotiByRequestId(requestId);
        notificationDAO.insertResponseCancelNotiByRequestId(requestId);

        //요청 상태 변경
        requestDAO.cancelOther(requestId);
        if (requestDAO.updateRequestStatus(requestId, RequestStatus.ACCEPTED) != 1) {
            throw new SQLException("요청 상태 변경에 실패했습니다");
        }

        //거래 생성
        TradeInsertDTO tradeInsertDTO = new TradeInsertDTO(requestId);
        if (tradeDAO.insertTradeByRequestId(tradeInsertDTO) != 1) {
            throw new SQLException("거래 생성에 실패했습니다");
        }

        TradeInfoDTO tradeInfoDTO = tradeDAO.getTradeInfoByTradeId(tradeInsertDTO.getId()); //지금 null인 상황
        System.out.println("======================================" + tradeInfoDTO.getRequestNickname());
        //ItemNotiDTO requestNotiDTO = itemDAO.getItemNoti(tradeInfoDTO.getRequestItemId());
        notificationDAO.insertNotification(new NotificationInsertDTO(tradeInfoDTO.getRequestMemberId(),
                        "'" + tradeInfoDTO.getResponseTitle() + "'로 보낸 요청이 수락되었어요","/trade/" + tradeInfoDTO.getId()));

        //거래 아이디 반환
        return tradeInsertDTO.getId();
    }

    @Transactional
    public void rejectRequest(long requestId) throws SQLException {
        if (requestDAO.updateRequestStatus(requestId, RequestStatus.REJECTED) != 1) {
            throw new SQLException("요청 상태 변경에 실패했습니다");
        }

        RequestInfoDTO requestInfoDTO = requestDAO.getRequestInfoByRequestId(requestId);
        notificationDAO.insertNotification(new NotificationInsertDTO(requestInfoDTO.getRequestMemberId(),
                "'" + requestInfoDTO.getResponseTitle() + "'로 보낸 요청이 거절되었어요","/item/detail/" + requestInfoDTO.getResponseItemId()));
    }

    @Transactional
    public void cancelRequest(long requestId) throws SQLException {
        if (requestDAO.updateRequestStatus(requestId, RequestStatus.CANCELED) != 1) {
            throw new SQLException("요청 상태 변경에 실패했습니다.");
        }
        RequestInfoDTO requestInfoDTO = requestDAO.getRequestInfoByRequestId(requestId);
        notificationDAO.insertNotification(new NotificationInsertDTO(requestInfoDTO.getResponseMemberId(),
                "'" + requestInfoDTO.getRequestTitle() + "'로 부터 받은 요청이 취소되었어요","/item/detail/" + requestInfoDTO.getRequestItemId()));
    }

    public TradeInfoDTO getTradeInfoByTradeId(long tradeId) {
        TradeInfoDTO tradeInfoDTO = tradeDAO.getTradeInfoByTradeId(tradeId);
        return tradeInfoDTO;
    }

    // 유저 거래중 리스트 조회
    public List<TradeDetailDTO> getTradeList(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException {
        return tradeDAO.getTradeListByMemberId(memberId, lastRequestId, request, offset);
    }

    // 유저 거래완료 리스트 조회
    public List<TradeDetailDTO> getTradeSucceedList(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException {
        return tradeDAO.getTradeSucceedListByMemberId(memberId, lastRequestId, request, offset);
    }

    //물품 거래 완료/취소 요청 -> 거래 상태 return
    @Transactional
    public TradeDecisionResultDTO sendDecision(long memberId, long tradeId, TradeDecision tradeDecision) throws InternalException {
        //거래 정보 불러오기
        TradeInfoDTO tradeInfoDTO = tradeDAO.getTradeInfoByTradeId(tradeId);

        int updatedTradeDecision = 0;
        //거래 요구사항 변경
        if (memberId == tradeInfoDTO.getRequestMemberId()) {
            updatedTradeDecision = tradeDAO.updateRequestDecision(tradeId, tradeDecision);
        } else if (memberId == tradeInfoDTO.getResponseMemberId()) {
            updatedTradeDecision = tradeDAO.updateResponseDecision(tradeId, tradeDecision);
        }

        if (updatedTradeDecision != 1) {
            throw new InternalException("처리 오류");
        }

        TradeDecisionResultDTO resultDTO = tradeDAO.getTradeDecisionResultByTradeId(tradeId);
        if (resultDTO.getTradeStatus() == TradeStatus.SUCCEED || resultDTO.getTradeStatus() == TradeStatus.CANCELED) {
            throw new InternalException("처리 오류");
        }

        //양 요구사항이 같다면 거래 상태 변경, 물품 상태 변경
        if (resultDTO.getRequestDecision() == resultDTO.getResponseDecision()) {
            int updatedTradeStatus  = 1;

            if (resultDTO.getRequestDecision() == TradeDecision.ACCEPT) {
                updatedTradeStatus = tradeDAO.updateTradeStatusByTradeId(tradeId, TradeStatus.SUCCEED);
                resultDTO.setTradeStatus(TradeStatus.SUCCEED);
                itemDAO.updateItemStatus(tradeInfoDTO.getRequestItemId(), ItemStatus.TRADED);
                itemDAO.updateItemStatus(tradeInfoDTO.getResponseItemId(), ItemStatus.TRADED);
                //요청자
                notificationDAO.insertNotification(new NotificationInsertDTO(tradeInfoDTO.getRequestMemberId(),
                        "'" + tradeInfoDTO.getResponseTitle() + "'와의 거래가 완료되었어요","/trade/myTrade?tab=complete"));
                //응답자
                notificationDAO.insertNotification(new NotificationInsertDTO(tradeInfoDTO.getResponseMemberId(),
                        "'" + tradeInfoDTO.getRequestTitle() + "'와의 거래가 완료되었어요","/trade/myTrade?tab=complete"));
            } else if (resultDTO.getRequestDecision() == TradeDecision.REJECT) {
                updatedTradeStatus = tradeDAO.updateTradeStatusByTradeId(tradeId, TradeStatus.CANCELED);
                resultDTO.setTradeStatus(TradeStatus.CANCELED);
                itemDAO.updateItemStatus(tradeInfoDTO.getRequestItemId(), ItemStatus.WAITING);
                itemDAO.updateItemStatus(tradeInfoDTO.getResponseItemId(), ItemStatus.WAITING);
                //알림 처리
                notificationDAO.insertNotification(new NotificationInsertDTO(tradeInfoDTO.getRequestMemberId(),
                        "'" + tradeInfoDTO.getResponseTitle() + "'와의 거래가 취소되었어요","/trade/myTrade?tab=complete"));
                //응답자
                notificationDAO.insertNotification(new NotificationInsertDTO(tradeInfoDTO.getResponseMemberId(),
                        "'" + tradeInfoDTO.getRequestTitle() + "'와의 거래가 취소되었어요","/trade/myTrade?tab=complete"));
            }

            if (updatedTradeStatus != 1) {
                throw new InternalException("처리 오류 발생");
            }
        }

        //거래 상태 반환
        return resultDTO;
    }

}
