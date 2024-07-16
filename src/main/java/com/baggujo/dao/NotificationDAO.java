package com.baggujo.dao;

import com.baggujo.dto.NotificationInsertDTO;
import com.baggujo.dto.enums.NotificationStatus;
import com.baggujo.dto.enums.TradeStatus;
import org.apache.ibatis.annotations.Mapper;

import javax.management.remote.TargetedNotification;
import java.sql.SQLException;

@Mapper
public interface NotificationDAO {
    int insertNotification(NotificationInsertDTO notificationInsertDTO);       //일반적인 알림 생성
    void insertRequestCancelNotiByRequestId(long requestId) throws SQLException;
    void insertResponseCancelNotiByRequestId(long requestId) throws SQLException;
    void updateNotificationById(long notificationId, NotificationStatus notificationStatus);
    void updateNotificationByMemberIdAndTradeId(long memberId, long tradeId, NotificationStatus notificationStatus);
    //void getNotifications(long lastNotificationId, int offset);
}
