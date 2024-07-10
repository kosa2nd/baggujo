package com.baggujo.controller.rest;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.RequestInsertDTO;
import com.baggujo.service.TradeService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/item")
public class RestTradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/request")
    public void insertRequest(@RequestBody RequestInsertDTO requestInsertDTO, @AuthenticationPrincipal AuthDTO authDTO) throws SQLException, BadRequestException {
        if(authDTO == null) {
            throw new BadRequestException("Invalid request");
        }

        try {
            tradeService.insertRequest(requestInsertDTO);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

}
