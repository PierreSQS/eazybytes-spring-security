package com.eazybytes.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CardsController.class)
class CardsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCardDetailsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/myCards"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @WithMockUser
    @Test
    void getCardDetailsWithMockUserAnnotated() throws Exception {
        mockMvc.perform(get("/myCards"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the card details from the DB"))
                .andDo(print());
    }

    @Test
    void getCardDetailsWithAnonymousUserNotAuthenticated() throws Exception {
        mockMvc.perform(get("/myCards").with(anonymous()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @WithAnonymousUser
    @Test
    void getCardDetailsWithAnonymousUserAnnotated() throws Exception {
        mockMvc.perform(get("/myCards"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getCardDetailsWithMockUserPostProcessor() throws Exception {
        mockMvc.perform(get("/myCards").with(user("Pierrot MockUser")))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the card details from the DB"))
                .andDo(print());
    }

}