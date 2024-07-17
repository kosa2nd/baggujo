package com.baggujo.controller;

import com.baggujo.dto.AuthDTO;
import com.baggujo.dto.SignupMemberDTO;
import com.baggujo.dto.UpdateTextMemberDTO;
import com.baggujo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/update")
    public String update(@AuthenticationPrincipal AuthDTO authDTO, Model model) {
        model.addAttribute("updateMember", memberService.getMemberById(authDTO.getId()));
        return "member/updatemember";
    }

    // 비밀번호 수정
//    @PostMapping("/update")
//    public String updatePassword(@RequestParam UpdatePasswordMemberDTO updatePasswordMemberDTO, @AuthenticationPrincipal AuthDTO authDTO, Model model) {
//        try{
//            updatePasswordMemberDTO.setPassword(passwordEncoder.encode(updatePasswordMemberDTO.getPassword()));
//            memberService.updatePasswordMember(password, authDTO.getId());
//            return "redirect:/";
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "비밀번호 수정 중 오류가 발생했습니다.");
//            return "member/login";
//        }
//    }

    // 이름, 닉네임, 전화번호 수정
    @PostMapping("/updatetext")
    public String updateText(@Validated UpdateTextMemberDTO updateTextMemberDTO, BindingResult result, @AuthenticationPrincipal AuthDTO authDTO, Model model) {
        System.out.println(updateTextMemberDTO);
        if (authDTO == null) {
            return "member/login";
        }
        if (result.hasErrors()) {
            return "member/updatemember";
        }
        try{
            memberService.updateTextMember(updateTextMemberDTO, authDTO.getId());
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", "회원정보 수정 중 오류가 발생했습니다.");
            return "member/updatemember";
        }
    }
}
