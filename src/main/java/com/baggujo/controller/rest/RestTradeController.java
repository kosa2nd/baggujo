package com.baggujo.controller.rest;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.RequestDTO;
import com.baggujo.dto.RequestInsertDTO;
import com.baggujo.service.RequestService;
import com.baggujo.service.TradeService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/item")
public class RestTradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private RequestService requestService;

    @PostMapping("/request")
    public void insertRequest(@RequestBody RequestInsertDTO requestInsertDTO, @AuthenticationPrincipal AuthDTO authDTO) throws SQLException, BadRequestException {
        if (authDTO == null) {
            throw new BadRequestException("Invalid request");
        }

        try {
            tradeService.insertRequest(requestInsertDTO);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/requestList")
    public List<RequestDTO> getRequestList(@RequestParam long lastRequestId, @RequestParam(required = false) Boolean request, @RequestParam long offset, @AuthenticationPrincipal AuthDTO authDTO) throws BadRequestException {
        if (authDTO == null) {
            throw new BadRequestException("Invalid request");
        }

        long memberId = authDTO.getId();

        try {
            return requestService.getRequests(memberId, lastRequestId, request, offset);
        } catch (SQLException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
