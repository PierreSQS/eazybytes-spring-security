package com.eazybytes.springsecuritysection3.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceControllerIT {

    @Autowired
    MockMvc mockMvc;

    private String usernameFromDB;
    private String pwdFromDB;

    @BeforeEach
    void setUp() {
        usernameFromDB = "johndoe@example.com";
        pwdFromDB = "54321";
    }

    @Test
    void getBalanceDetailsWithBasicAuthenticationOK() throws Exception {

        mockMvc.perform(get("/myBalance").with(httpBasic(usernameFromDB,pwdFromDB)))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }

    @Test
    void getBalanceDetailsWithAnonymousNOK() throws Exception {
        mockMvc.perform(get("/myBalance").with(anonymous()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getBalanceDetailsWithMockUserOK() throws Exception {
        mockMvc.perform(get("/myBalance").with(user("Pierrot MockUser1")))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }

    @WithMockUser(username = "Pierrot MockUser2")
    @Test
    void getBalanceDetailsWithMockUserInAnnotationOK() throws Exception {
        mockMvc.perform(get("/myBalance"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void getBalanceDetailsWithMockUserDefaultOK() throws Exception {
        mockMvc.perform(get("/myBalance"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here are the balance details from the DB"))
                .andDo(print());
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
    void getBalanceDetailsWithFormloginOK() throws Exception {
        FormLoginRequestBuilder formLoginRequestBuilder = formLogin().user(usernameFromDB).password(pwdFromDB);
        mockMvc.perform(formLoginRequestBuilder)
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }
}