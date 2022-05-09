package com.eazybytes.controller;


import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.model.Customer;
import com.eazybytes.repository.CustomerRepository;

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
