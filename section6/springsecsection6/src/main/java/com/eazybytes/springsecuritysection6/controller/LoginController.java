package com.eazybytes.springsecuritysection6.controller;


import com.eazybytes.springsecuritysection6.model.Customer;
import com.eazybytes.springsecuritysection6.repository.CustomerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

	private final CustomerRepository customerRepository;

	public LoginController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@RequestMapping("/user")
	public Customer getUserDetailsAfterLogin(Principal user) {
		return customerRepository.findByEmail(user.getName()).orElse(null);

	}

}
