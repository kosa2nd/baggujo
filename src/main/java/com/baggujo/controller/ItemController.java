package com.baggujo.controller;

import com.baggujo.dto.ItemInsertDTO;
import com.baggujo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    //@Autowired
    //private ItemService itemService;

    @GetMapping("/insert")
    public String insert() {
        return "item/insert";
    }

    @PostMapping("/insert")
    public String submitPost(@ModelAttribute ItemInsertDTO itemInsertDTO) {
        try {
            ItemInsertDTO insertedItem = itemService.insertItem(itemInsertDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/item/list";
    }

}
