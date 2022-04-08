package com.eazybytes.springsecuritysection6.controller;

import com.eazybytes.springsecuritysection6.model.Customer;
import com.eazybytes.springsecuritysection6.model.Loans;
import com.eazybytes.springsecuritysection6.repository.LoanRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoansControllerTest {

    private Customer customerMock;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LoanRepository loanRepoMock;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        customerMock = new Customer();
        customerMock.setId(1);
        customerMock.setEmail("happy@example.com");
        customerMock.setMobileNumber("12313131331");
        customerMock.setPwd("12345");
        customerMock.setRole("admin");

        Loans loans1 = new Loans();
        loans1.setCustomerId(customerMock.getId());
        loans1.setLoanNumber(1111);
        loans1.setAmountPaid(5555);

        Loans loans2 = new Loans();
        loans2.setCustomerId(customerMock.getId());
        loans2.setLoanNumber(2222);
        loans2.setAmountPaid(6666);

        given(loanRepoMock.findByCustomerIdOrderByStartDtDesc(customerMock.getId())).willReturn(List.of(loans1,loans2));
    }

    @Test
    void getLoanDetails() throws Exception {
        mockMvc.perform(post("/myLoans").with(httpBasic("happy@examle.com", "12345"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMock)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}