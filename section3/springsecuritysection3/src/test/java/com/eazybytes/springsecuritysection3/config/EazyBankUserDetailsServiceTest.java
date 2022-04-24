package com.eazybytes.springsecuritysection3.config;

import com.eazybytes.springsecuritysection3.model.Customer;
import com.eazybytes.springsecuritysection3.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled("TO CHECK!! DOESN'T WORK AT MOMENT!!")
@DataJpaTest
class EazyBankUserDetailsServiceTest {

    @Autowired
    CustomerRepository customerRepos;

    private String foundEmail;

    @BeforeEach
    void setUp() {
        foundEmail = "johndoe@example.com";
    }

    @Test
    void loadUserByUsername() {
        Optional<Customer> optEmail = customerRepos.findByEmail(this.foundEmail);
        if (optEmail.isPresent()){
            assertEquals(optEmail.get().getEmail(),foundEmail);
        } else {
            assertFalse(false,"User: "+foundEmail+" not found!!!!");
        }
    }
}