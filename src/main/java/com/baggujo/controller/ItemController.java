package com.baggujo.controller;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.CategoryDTO;
import com.baggujo.dto.ItemDetailDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/insert")
    public String insert(Model model) {
        List<CategoryDTO> categories = itemService.getCategories();
        model.addAttribute("categories", categories);
        return "item/insert";
    }

    @PostMapping("/insert")
    public String submitPost(@Validated @ModelAttribute ItemInsertDTO itemInsertDTO, BindingResult bindingResult, Model model) {
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
            model.addAttribute("errorMessage", "게시글 등록 중 오류가 발생했습니다.");
            return "item/insert";
        }
    }


    @GetMapping("detail/{id}")
    public String getItemDetail(@PathVariable("id") long id, Model model) throws SQLException {
        ItemDetailDTO itemDetail = itemService.getItemDetailById(id);
        model.addAttribute("itemDetail", itemDetail);
        return "item/detail";
    }

}
