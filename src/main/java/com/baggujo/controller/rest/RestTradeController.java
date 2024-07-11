package com.baggujo.controller.rest;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.RequestDTO;
import com.baggujo.dto.RequestInsertDTO;
import com.baggujo.service.RequestService;
import com.baggujo.service.TradeService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getRequestList(@RequestParam long lastRequestId, @RequestParam(required = false) Boolean request, @RequestParam(defaultValue = "12") long offset, @AuthenticationPrincipal AuthDTO authDTO) throws BadRequestException {
        Map<String, Object> map = new HashMap<>();
        List<RequestDTO> requestDTOS;
        if (authDTO == null) {
            throw new BadRequestException("Invalid request");
        }

        long memberId = authDTO.getId();

        try {
            requestDTOS = requestService.getRequestList(memberId, lastRequestId, request, offset);
            map.put("requestList", requestDTOS);

            long lastItemId = -1;

            if (!requestDTOS.isEmpty()) {
                lastItemId = requestDTOS.get(requestDTOS.size() - 1).getId();
            } else {
                map.put("finished", true);
            }

            map.put("lastItemId", lastItemId);
        } catch (SQLException e) {
            throw new BadRequestException(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/accept")
    public ResponseEntity<Map<String, Object>> acceptRequest(@RequestParam long requestId) {
        Map<String, Object> response = new HashMap<>();
        try {
            long tradeId = tradeService.acceptRequest(requestId);
            response.put("message", "Request accepted successfully");
            response.put("tradeId", tradeId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
