package com.baggujo.controller;

import com.baggujo.dto.SignupMemberDTO;
import com.baggujo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {

        if (error != null) {
            model.addAttribute("error", "아이디 혹은 비밀번호가 일치하지 않습니다");
        }

        return "/member/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Validated @ModelAttribute SignupMemberDTO signupMemberDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "member/signup";
        }
        signupMemberDTO.setPassword(passwordEncoder.encode(signupMemberDTO.getPassword()));
        try {
            memberService.signUpMember(signupMemberDTO);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원가입 중 오류가 발생했습니다.");
            return "member/signup";
        }
    }
}
