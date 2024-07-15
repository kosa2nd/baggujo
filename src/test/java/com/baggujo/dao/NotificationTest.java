package com.baggujo.dao;

import com.baggujo.dto.NotificationInsertDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationTest {
    @Autowired
    private NotificationDAO notificationDAO;

    @Test
    public void 알림생성테스트() {
        notificationDAO.insertNotification(new NotificationInsertDTO(1, "알림 내용", "/", false, 41));
    }
}
