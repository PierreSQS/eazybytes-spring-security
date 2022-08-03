package com.eazybytes.controller;

import com.eazybytes.model.Cards;
import com.eazybytes.model.Customer;
import com.eazybytes.repository.CardsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CardsController.class})
class CardsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CardsRepository cardsRepoMock;

    @Autowired
    ObjectMapper objectMapper;

    Customer customerMock;

    Cards card1;
    Cards card2;

    @BeforeEach
    void setUp() {
        customerMock = new Customer();
        customerMock.setId(1);
        customerMock.setName("PierrotTest");
        customerMock.setCreateDt(LocalDate.now().toString());
        customerMock.setEmail("pierrottest@example.com");
        customerMock.setPwd("12345");

        // CARDS
        card1 = new Cards();
        card1.setCardId(1);
        card1.setCardNumber("45454545445");
        card1.setCreateDt(LocalDate.of(2020,4,6));
        card1.setAmountUsed(1000);
        card1.setAvailableAmount(2500);
        card1.setTotalLimit(7500);

        card2 = new Cards();
        card2.setCardId(2);
        card2.setCardNumber("46464646464");
        card2.setCreateDt(LocalDate.of(2021,8,6));
        card2.setAmountUsed(2000);
        card2.setAvailableAmount(2400);
        card2.setTotalLimit(4000);

    }

    @Test
    @WithMockUser
    void getCardDetails() throws Exception {
        // GIVEN
        given(cardsRepoMock.findByEmail(anyString())).willReturn(List.of(card1,card2));

        // WHEN, THEN
        mockMvc.perform(post("/myCards").with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customerMock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].cardNumber").value(equalTo("46464646464")))
                .andExpect(jsonPath("$.length()").value(equalTo(2)))
                .andDo(print());
    }
}