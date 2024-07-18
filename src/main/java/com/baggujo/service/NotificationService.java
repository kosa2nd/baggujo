package com.baggujo.service;

import com.baggujo.dao.NotificationDAO;
import com.baggujo.dto.NotificationDetailDTO;
import com.baggujo.dto.enums.NotificationStatus;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationDAO notificationDAO;

    public void removeAllChat(long memberId, long tradeId) {
        notificationDAO.updateNotificationByMemberIdAndTradeId(memberId, tradeId, NotificationStatus.DELETED);
    }

    @Transactional
    public void updateNotification(long notificationId, NotificationStatus notificationStatus) {
        if (notificationDAO.updateNotificationById(notificationId, notificationStatus) != 1) {
            throw new InternalException("알림 처리에 실패했습니다.");
        }
    }

    public List<NotificationDetailDTO> getNotifications(long memberId) {
        return notificationDAO.getNotifications(memberId);
    }
}
