package com.baggujo.dao;

import com.baggujo.dto.NotificationInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface NotificationDAO {
    int insertNotification(NotificationInsertDTO notificationInsertDTO);       //일반적인 알림 생성
    void insertRequestCancelNotiByRequestId(long requestId) throws SQLException;
    void insertResponseCancelNotiByRequestId(long requestId) throws SQLException;
}
