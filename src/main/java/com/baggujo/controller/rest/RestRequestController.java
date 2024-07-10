package com.baggujo.controller.rest;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.RequestUserItemDTO;
import com.baggujo.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RestRequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/request")
    public List<RequestUserItemDTO> getRequestItems(@AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return null; // 로그인되지 않은 경우에 대한 처리
        }

        long memberId = authDTO.getId();
        return requestService.getRequestUserItems(memberId);
    }

}
