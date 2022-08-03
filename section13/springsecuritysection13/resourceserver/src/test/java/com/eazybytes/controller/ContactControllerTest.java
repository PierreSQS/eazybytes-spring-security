package com.eazybytes.controller;

import com.eazybytes.model.Contact;
import com.eazybytes.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ContactController.class})
class ContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ContactRepository contactMockRepo;

    Contact contactMock;
    Contact savedContactMock;

    @BeforeEach
    void setUp() {
        contactMock = new Contact();
        contactMock.setContactEmail("pierrottest@example.com");
        contactMock.setContactName("Pierrot Test");
        contactMock.setMessage("Please contact me");
        contactMock.setSubject("My Credit Card");

        savedContactMock = new Contact();
        savedContactMock.setContactId("SR121231313");
        savedContactMock.setContactEmail("pierrottest@example.com");
        savedContactMock.setContactName("Pierrot Test");
        savedContactMock.setMessage("Please contact me");
        savedContactMock.setSubject("My Credit Card");

    }

    @Test
    void saveContactInquiryDetails() throws Exception {

        // Given
        String contactAsString = objectMapper.writeValueAsString(contactMock);
        given(contactMockRepo.save(any())).willReturn(savedContactMock);

        // When , then
        mockMvc.perform(post("/contact").with(user("Mock User").password("Mock PWD")).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contactAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactName",equalTo("Pierrot Test")))
                .andExpect(jsonPath("$.contactEmail").value("pierrottest@example.com"))
                .andDo(print());
    }

}