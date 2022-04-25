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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    private String username;
    private String pwd;
    private Customer customer;

    @BeforeEach
    void setUp() {
        username = "happy@example.com";
        pwd = "12345";

        customer = new Customer();
        customer.setId(1);
        customer.setEmail("happy@example.com");
    }

    @Test
    void getBalanceDetails() throws Exception {
        mockMvc.perform(post("/myBalance")
                            .with(csrf())
                            .with(user(username).password(pwd))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(equalTo(6)))
                .andDo(print());
    }
}