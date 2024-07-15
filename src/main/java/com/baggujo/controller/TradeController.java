package com.baggujo.controller;

import com.baggujo.dto.*;
import com.baggujo.service.ChatService;
import com.baggujo.service.TradeService;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    private TradeService tradeService;
    @Autowired
    private ChatService chatService;

    @GetMapping("/myTrade")
    public String myTrade(@AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return "redirect:/member/login";
        }

        return "/myTrade";
    }

    @GetMapping("/{tradeId}")
    public String doTrade(@PathVariable long tradeId, Model model, @AuthenticationPrincipal AuthDTO authDTO) {

        if (authDTO == null) {
            return "redirect:/member/login";
        }

        TradeInfoDTO tradeInfoDTO;
        try {
            tradeInfoDTO = tradeService.getTradeInfoByTradeId(tradeId);

            long memberId = authDTO.getId();
            if (memberId != tradeInfoDTO.getRequestMemberId() && memberId != tradeInfoDTO.getResponseMemberId()) {
                return "redirect:/";
            }

        } catch (NullPointerException e) {
            return "redirect:/";
        }

        model.addAttribute("chats", chatService.getChat(tradeId));
        model.addAttribute("tradeInfoDTO", tradeInfoDTO);
        return "/trade/dotrade";
    }

}
