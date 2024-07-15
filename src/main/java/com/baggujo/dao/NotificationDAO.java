package com.baggujo.dao;

import com.baggujo.dto.NotificationInsertDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationDAO {
    //c
    int insertNotification(NotificationInsertDTO notificationInsertDTO);       //일반적인 알림 생성
}
