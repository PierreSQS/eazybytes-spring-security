package com.eazybytes.springsecuritysection3.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BalanceController.class})
class BalanceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getBalanceDetailsWithBasicAuthenticationOK() throws Exception {
        mockMvc.perform(get("/myBalance").with(httpBasic("Pierrot","Pierrot123")))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }

    /*
    * fails while providing custom config e.g. Configuration Java file
    * */
    @Test
    void getBalanceDetailsWithAnonymousOK() throws Exception {
        mockMvc.perform(get("/myBalance").with(anonymous()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getBalanceDetailsWithMockUserOK() throws Exception {
        mockMvc.perform(get("/myBalance").with(user("MockUser")))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }

    @WithMockUser(username = "MockUser")
    @Test
    void getBalanceDetailsWithMockUserInAnnotationOK() throws Exception {
        mockMvc.perform(get("/myBalance"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void getBalanceDetailsWithMockUserSimple() throws Exception {
        mockMvc.perform(get("/myBalance"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }

    @Test
    void getBalanceDetailsWithFormloginOK() throws Exception {
        FormLoginRequestBuilder formLoginRequestBuilder = formLogin().user("Pierrot").password("Pierrot123");
        mockMvc.perform(formLoginRequestBuilder)
                .andExpect(authenticated())
                .andDo(print());
    }
}