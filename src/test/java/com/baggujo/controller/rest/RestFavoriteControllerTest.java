package com.baggujo.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
class RestFavoriteControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    @WithMockUser
    @DisplayName("해더에 오나 바디에 오나")
    void test1() throws Exception {
        long itemId = 1L;
       mockMvc.perform(post("/favorite/toggle/{itemId}",itemId)).andDo(print());
    }



}