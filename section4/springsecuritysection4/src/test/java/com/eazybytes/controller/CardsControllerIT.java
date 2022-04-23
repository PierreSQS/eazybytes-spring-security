package com.eazybytes.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardsControllerIT {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @WithUserDetails("happy@example.com")
    @Test
    void getCardDetailsWithUserDetailsOK() throws Exception {
        mockMvc.perform(get("/myCards"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the card details from the DB"))
                .andDo(print());
    }

}