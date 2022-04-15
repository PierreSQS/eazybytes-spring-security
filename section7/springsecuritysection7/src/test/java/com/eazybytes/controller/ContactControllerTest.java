package com.eazybytes.controller;

import com.eazybytes.application.EazyBankBackendApplication;
import com.eazybytes.model.Contact;
import com.eazybytes.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
@ContextConfiguration(classes = {EazyBankBackendApplication.class})
@AutoConfigureDataJpa
class ContactControllerTest {

    @MockBean
    ContactRepository contactRepoMock;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Contact contactMock;

    Contact savedContactMock;

    @BeforeEach
    void setUp() {
        contactMock = new Contact();
        contactMock.setContactEmail("pierrot@example.com");
        contactMock.setContactName("Pierrot");
        contactMock.setMessage("Please contact me");
        contactMock.setSubject("My Credit Card");

        savedContactMock = new Contact();
        savedContactMock.setContactId("SR663396654");
        savedContactMock.setContactEmail("pierrot@example.com");
        savedContactMock.setContactName("Pierrot");
        savedContactMock.setMessage("Please contact me");
        savedContactMock.setSubject("My Credit Card");

    }

    @Test
    void saveContactInquiryDetails() throws Exception {

        // Given
        String contactAsString = objectMapper.writeValueAsString(contactMock);
        given(contactRepoMock.save(any())).willReturn(savedContactMock);

        // When , then
        mockMvc.perform(post("/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contactAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactName",equalTo("Pierrot")))
                .andExpect(jsonPath("$.contactEmail").value("pierrot@example.com"))
                .andDo(print());

        verify(contactRepoMock).save(any());

    }

}