package com.baggujo.controller;

import com.baggujo.dto.*;
import com.baggujo.security.dto.MemberAuthDTO;
import com.baggujo.service.FavoriteService;
import com.baggujo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/insert")
    public String insert(Model model) {
        List<CategoryDTO> categories = itemService.getCategories();
        model.addAttribute("categories", categories);
        return "item/insert";
    }

    @PostMapping("/insert")
    public String submitPost(@Validated @ModelAttribute ItemInsertDTO itemInsertDTO, @AuthenticationPrincipal AuthDTO authDTO,BindingResult bindingResult, Model model) {
        if(authDTO == null) {
            return "redirect:/member/login";
        }

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            boolean missingValue = false;
            for (FieldError error : bindingResult.getFieldErrors()) {
                sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
                if (Objects.requireNonNull(error.getDefaultMessage()).contains("null")) {
                    missingValue = true;
                }
            }
            if (missingValue) {
                sb.insert(0, "모든 값을 입력해주세요. \n");
            }
            model.addAttribute("errorMessage", sb.toString());
            model.addAttribute("categories", itemService.getCategories());
            return "item/insert";
        }

        try {
            long id = itemService.insertItem(itemInsertDTO);
            return "redirect:/item/detail/" + id;
        } catch (Exception e) {
            e.printStackTrace();

            model.addAttribute("categories", itemService.getCategories());
            model.addAttribute("errorMessage", "게시글 등록 중 오류가 발생했습니다.");
            return "item/insert";
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") long id, Model model, @AuthenticationPrincipal AuthDTO authDTO) throws SQLException {
        if (authDTO == null) {
            return "redirect:/member/login";
        }

        ItemDetailDTO itemDetail = itemService.getItemDetailById(id);
        model.addAttribute("itemDetail", itemDetail);
        model.addAttribute("categories", itemService.getCategories());
        return "item/update";
    }

    @GetMapping("/detail/{id}")
    public String getItemDetail(@PathVariable("id") long id, @AuthenticationPrincipal MemberAuthDTO memberAuthDTO, Model model) throws SQLException {
        ItemDetailDTO itemDetail = itemService.getItemDetailById(id);
        model.addAttribute("itemDetail", itemDetail);
        return "item/detail";
    }

    @GetMapping("/myfavorite")
    public String getMyFavoriteItems(@AuthenticationPrincipal AuthDTO authDTO, Model model) {
        if (authDTO == null) {
            return "redirect:/member/login";
        }
        return "item/myfavorite";
    }

    @GetMapping("/myitems")
    public String getMyItems(@AuthenticationPrincipal AuthDTO authDTO) {
        if (authDTO == null) {
            return "redirect:/member/login";
        }
        return "item/myitems";
    }
}
