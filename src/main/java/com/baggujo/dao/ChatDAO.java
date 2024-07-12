package com.baggujo.dao;

import com.baggujo.dto.ChatInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ChatDAO {

    int insertChat(ChatInsertDTO chatDTO) throws SQLException;
    List<ChatInsertDTO> getChat(long id);
}
