package com.eazybytes.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceControllerIT {

    @Autowired
    MockMvc mockMvc;

    private String username;
    private String pwd;


    @BeforeEach
    void setUp() {
        username = "johndoe@example.com";
        pwd = "54321";
    }

    @WithUserDetails("johndoe@example.com")
    @Test
    void getBalanceDetailsWithUserDetailsAnnotatedOK() throws Exception {
        mockMvc.perform(get("/myBalance"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }

    @Test
    void getBalanceDetailsWithHttpBasicOK() throws Exception {
        mockMvc.perform(get("/myBalance").with(httpBasic(username,pwd)))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }
}