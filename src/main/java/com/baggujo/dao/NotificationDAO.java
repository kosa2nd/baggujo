package com.baggujo.dao;

import com.baggujo.dto.NotificationDetailDTO;
import com.baggujo.dto.NotificationInsertDTO;
import com.baggujo.dto.enums.NotificationStatus;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface NotificationDAO {
    int insertNotification(NotificationInsertDTO notificationInsertDTO);       //일반적인 알림 생성
    void insertRequestCancelNotiByRequestId(long requestId) throws SQLException;
    void insertResponseCancelNotiByRequestId(long requestId) throws SQLException;
    int updateNotificationById(long notificationId, NotificationStatus notificationStatus);
    void updateNotificationByMemberIdAndTradeId(long memberId, long tradeId, NotificationStatus notificationStatus);
    List<NotificationDetailDTO> getNotifications(long memberId);
}
