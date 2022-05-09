package com.eazybytes.controller;

import com.eazybytes.model.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
@AutoConfigureDataJpa
class ContactControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact();
        contact.setContactEmail("pierrottest@example.com");
        contact.setContactName("Pierrot Test");
        contact.setMessage("Please contact me");
        contact.setSubject("My Credit Card");

    }

    @Test
    void saveContactInquiryDetails() throws Exception {

        // Given
        String contactAsString = objectMapper.writeValueAsString(contact);

        // When , then
        mockMvc.perform(post("/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contactAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactName",equalTo("Pierrot Test")))
                .andExpect(jsonPath("$.contactEmail").value("pierrottest@example.com"))
                .andDo(print());
    }

}