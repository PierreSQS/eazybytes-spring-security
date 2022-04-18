package com.eazybytes.controller;

import com.eazybytes.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardsControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Customer customerMock;

    @BeforeEach
    void setUp() {
        customerMock = new Customer();
        customerMock.setId(1);

    }

    @Test
    void getCardDetailsWithGoodUser() throws Exception {

        String username = "happy@example.com";
        String password = "12345";

        mockMvc.perform(post("/myCards").with(csrf()).with(httpBasic(username,password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].cardNumber").value(equalTo("3455XXXX8673")))
                .andExpect(jsonPath("$.length()").value(3))
                .andDo(print());
    }

    @Test
    void getCardDetailsWithWrongUser() throws Exception {

        String username = "pierrot@example.com";
        String password = "12345";

        mockMvc.perform(post("/myCards").with(csrf()).with(httpBasic(username,password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMock)))
                .andExpect(unauthenticated())
                .andDo(print());
    }

    @Test
    @WithUserDetails("happy@example.com")
    void getCardDetailsWithUserDetails() throws Exception {

        mockMvc.perform(post("/myCards").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMock)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}