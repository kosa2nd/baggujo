package com.baggujo.controller;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.CategoryDTO;
import com.baggujo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public String home(Model model) {
        List<CategoryDTO> categories = itemService.getCategories();
        model.addAttribute("categories", categories);
        return "/index";
    }

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return "redirect:/member/login";
        }

        return "/myPage";
    }
}
