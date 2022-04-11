package com.eazybytes.springsecuritysection6.controller;

import com.eazybytes.springsecuritysection6.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.springsecuritysection6.model.Accounts;
import com.eazybytes.springsecuritysection6.model.Customer;

@RestController
public class AccountController {
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		return accountsRepository.findByCustomerId(customer.getId()).orElse(null);
	}

}
