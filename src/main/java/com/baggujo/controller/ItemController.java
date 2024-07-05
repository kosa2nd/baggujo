package com.baggujo.controller;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.ItemInsertDTO;
import com.baggujo.dto.enums.ItemCondition;
import com.baggujo.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/insert")
    public String insert(Model model) {
        return "item/insert";
    }

    @PostMapping("/insert")
    public String submitPost(@ModelAttribute ItemInsertDTO itemInsertDTO, MultipartFile[] multipartFiles) {
        try {
            long id = itemService.insertItem(itemInsertDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/item/list";
    }

}
