package com.baggujo.controller;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.TradeDetailDTO;
import com.baggujo.dto.TradeInfoDTO;
import com.baggujo.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    private TradeService tradeService;

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

        model.addAttribute("tradeInfoDTO", tradeInfoDTO);
        return "/trade/dotrade";
    }

}
