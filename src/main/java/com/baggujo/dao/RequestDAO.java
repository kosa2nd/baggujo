package com.baggujo.dao;

import com.baggujo.dto.RequestDTO;
import com.baggujo.dto.RequestInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface RequestDAO {

    void insertRequest(RequestInsertDTO requestInsertDTO) throws SQLException;
    List<RequestDTO> getRequestByMemberId(long memberId, long lastRequestId, Boolean request, long offset) throws SQLException;

}
