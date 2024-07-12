package com.baggujo.service;

import com.baggujo.dao.ChatDAO;
import com.baggujo.dto.ChatInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ChatService {
    @Autowired
    ChatDAO chatDAO;

    // 채팅 생성
    public void insertChat(ChatInsertDTO chatInsertDTO) throws SQLException {
        chatDAO.insertChat(chatInsertDTO);
    }
}
