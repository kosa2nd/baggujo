package com.baggujo.controller;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.TradeDetailDTO;
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
    public String myTrade(Model model) {

        return "/myTrade";
    }

    @GetMapping("/{tradeId}")
    public String doTrade(@PathVariable long tradeId, Model model, @AuthenticationPrincipal AuthDTO authDTO) {
        System.out.println("매핑 성공===============================");


        if (authDTO == null) {
            return "redirect:/member/login";
        }

        TradeDetailDTO tradeDetailDTO;
        try {
            tradeDetailDTO = tradeService.getTradeDetailByTradeId(tradeId);

            long memberId = authDTO.getId();
            if (memberId != tradeDetailDTO.getRequestMemberId() && memberId != tradeDetailDTO.getResponseMemberId()) {
                System.out.println("no participant====================");
                return "redirect:/";
            }

        } catch (NullPointerException e) {
            System.out.println("null=====================");
            return "redirect:/";
        }

        model.addAttribute("tradeDetailDTO", tradeDetailDTO);
        return "/trade/dotrade";
    }

}
