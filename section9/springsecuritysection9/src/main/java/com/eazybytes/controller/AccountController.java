package com.eazybytes.controller;

import com.eazybytes.model.Accounts;
import com.eazybytes.model.Customer;
import com.eazybytes.repository.AccountsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	
	private final AccountsRepository accountsRepository;

	public AccountController(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		return accountsRepository.findByCustomerId(customer.getId());

	}

}
