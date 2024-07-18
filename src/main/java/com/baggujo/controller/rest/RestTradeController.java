package com.baggujo.controller.rest;

import com.baggujo.dto.*;
import com.baggujo.dto.enums.TradeDecision;
import com.baggujo.service.RequestService;
import com.baggujo.service.TradeService;
import org.apache.logging.log4j.util.InternalException;
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
    public ResponseEntity<Boolean> insertRequest(@RequestBody RequestInsertDTO requestInsertDTO, @AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            tradeService.insertRequest(requestInsertDTO);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/requestList")
    public ResponseEntity<Map<String, Object>> getRequestList(@RequestParam long lastRequestId, @RequestParam(required = false) Boolean request, @RequestParam(defaultValue = "12") long offset, @AuthenticationPrincipal AuthDTO authDTO) {
        Map<String, Object> map = new HashMap<>();
        List<RequestDTO> requestDTOS;
        if (authDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
        } catch (SQLException | IndexOutOfBoundsException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/tradeList")
    public ResponseEntity<Map<String, Object>> getTradeList(@RequestParam long lastRequestId, @RequestParam(required = false) Boolean request, @RequestParam(defaultValue = "12") long offset, @AuthenticationPrincipal AuthDTO authDTO){
        Map<String, Object> map = new HashMap<>();
        List<TradeDetailDTO> tradeDTOs;

        if (authDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        long memberId = authDTO.getId();

        try {
            tradeDTOs = tradeService.getTradeList(memberId, lastRequestId, request, offset);
            map.put("tradeList", tradeDTOs);

            long lastItemId = -1;

            if (!tradeDTOs.isEmpty()) {
                lastItemId = tradeDTOs.get(tradeDTOs.size() - 1).getId();
            } else {
                map.put("finished", true);
            }

            map.put("lastItemId", lastItemId);
        } catch (SQLException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/succeedList")
    public ResponseEntity<Map<String, Object>> getTradeSucceedList(@RequestParam long lastRequestId, @RequestParam(required = false) Boolean request, @RequestParam(defaultValue = "12") long offset, @AuthenticationPrincipal AuthDTO authDTO) {
        Map<String, Object> map = new HashMap<>();
        List<TradeDetailDTO> tradeDTOs;

        if (authDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        long memberId = authDTO.getId();

        try {
            tradeDTOs = tradeService.getTradeSucceedList(memberId, lastRequestId, request, offset);
            map.put("tradeSucceedList", tradeDTOs);

            long lastItemId = -1;

            if (!tradeDTOs.isEmpty()) {
                lastItemId = tradeDTOs.get(tradeDTOs.size() - 1).getId();
            } else {
                map.put("finished", true);
            }

            map.put("lastItemId", lastItemId);
        } catch (SQLException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/accept")
    public ResponseEntity<Map<String, Object>> acceptRequest(@RequestParam long requestId, @AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

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

    @PostMapping("/reject")
    public ResponseEntity<Map<String, Object>> rejectRequest(@RequestParam long requestId, @AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> response = new HashMap<>();
        try {
            tradeService.rejectRequest(requestId);
            response.put("message", "Request rejected successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<Map<String, Object>> cancelRequest(@RequestParam long requestId, @AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> response = new HashMap<>();
        try {
            tradeService.cancelRequest(requestId);
            response.put("message", "Request cancelled successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/decision")
    public ResponseEntity<TradeDecisionResultDTO> decision(@RequestParam long memberId, @RequestParam long tradeId, @RequestParam TradeDecision tradeDecision, @AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null || authDTO.getId() != memberId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            TradeDecisionResultDTO tradeDecisionResultDTO = tradeService.sendDecision(authDTO.getId(), tradeId, tradeDecision);
            return new ResponseEntity<>(tradeDecisionResultDTO, HttpStatus.OK);
        } catch (InternalException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
