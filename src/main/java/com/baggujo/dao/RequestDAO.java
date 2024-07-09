package com.baggujo.dao;

import com.baggujo.dto.RequestDTO;
import com.baggujo.dto.RequestInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RequestDAO {

    void insertRequest(RequestInsertDTO requestInsertDTO);
    List<RequestDTO> getRequestByMemberId(long memberId, long lastRequestId, boolean request, long offset);

}
