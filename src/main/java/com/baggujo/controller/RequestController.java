//package com.baggujo.controller;
//
//import com.baggujo.dto.AuthDTO;
//import com.baggujo.dto.RequestUserItemDTO;
//import com.baggujo.service.RequestService;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/item")
//public class RequestController {
//
//    private final RequestService requestService;
//
//    public RequestController(RequestService requestService) {
//        this.requestService = requestService;
//    }
//
//    @GetMapping("/request/{id}")
//    public String request(@PathVariable("id") String id, @AuthenticationPrincipal AuthDTO authDTO, Model model) {
//
//        if (authDTO == null) {
//            return "redirect:/member/login";
//        }
//
//        long memberId = authDTO.getId();
//        List<RequestUserItemDTO> itemList = requestService.getRequestUserItems(memberId);
//
//        System.out.println("itemList : " + itemList);
//        model.addAttribute("itemList", itemList);
//        return "item/request";
//    }
//}
