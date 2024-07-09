package com.baggujo.controller.rest;

import com.baggujo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/mamber")
public class RestMemberController {

    @Autowired
    private MemberService memberService;

    public ResponseEntity<Boolean> dupliAccount(@RequestParam String email) {
        try{
            boolean duplicated = memberService.getEmailDuplicated(email);
            return new ResponseEntity<>(duplicated, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
