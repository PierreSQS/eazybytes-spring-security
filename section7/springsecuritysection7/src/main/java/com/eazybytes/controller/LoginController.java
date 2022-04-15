package com.eazybytes.controller;


import com.eazybytes.model.Customer;
import com.eazybytes.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@RequestMapping("/user")
	public Customer getUserDetailsAfterLogin(Principal user) {
		return customerRepository.findByEmail(user.getName())
				.orElse(null);
	}

}
