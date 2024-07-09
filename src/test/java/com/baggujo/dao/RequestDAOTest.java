package com.baggujo.dao;

import com.baggujo.dto.RequestDTO;
import com.baggujo.dto.RequestInsertDTO;
import com.baggujo.dto.enums.RequestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RequestDAOTest {

    @Autowired
    private RequestDAO requestDAO;

    @Test
    public void 요청생성테스트() {
        requestDAO.insertRequest(new RequestInsertDTO(121, 122, RequestStatus.WAITING));
    }

    @Test
    public void 요청목록가져오기테스트() {
        List<RequestDTO> dtos = requestDAO.getRequestByMemberId(1, 0, true, 12);
        for (RequestDTO dto: dtos) {
            System.out.println(dto);
        }
        //long memberId, long lastRequestId, boolean request, long offset)
    }

}
