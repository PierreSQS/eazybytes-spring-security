package com.eazybytes.controller;

import com.eazybytes.application.EazyBankBackendApplication;
import com.eazybytes.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BalanceController.class)
@ContextConfiguration(classes = {EazyBankBackendApplication.class})
@AutoConfigureDataJpa
class BalanceControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1);
        customer.setEmail("happy@example.com");
    }

    @Test
    void getBalanceDetailsWithUserPostProcessorOK() throws Exception {
        String username = "mockuser@example.com"; // Mock User
        String pwd = "123456"; // thus, any password will match!!!
        mockMvc.perform(post("/myBalance")
                        .with(csrf())
                        .with(user(username).password(pwd))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(equalTo(6)))
                .andDo(print());
    }

    // Correct in the context, because the user has the role admin not ROLE_USER
    // thus isForbidden (compare to test above)
    @WithUserDetails("happy@example.com")
    @Test
    void getBalanceDetailsWithUserDetailsAnnotatedOK() throws Exception {
        mockMvc.perform(post("/myBalance")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void getBalanceDetailsWithHttpBasicAuthenticationOK() throws Exception {
        String username = "happy@example.com"; // The Real User
        String pwd = "12345"; // The correct one
        mockMvc.perform(post("/myBalance")
                        .with(csrf())
                        .with(httpBasic(username,pwd))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(equalTo(6)))
                .andDo(print());
    }

}