package com.eazybytes.springsecuritysection6.controller;

import com.eazybytes.springsecuritysection6.model.AccountTransactions;
import com.eazybytes.springsecuritysection6.model.Customer;
import com.eazybytes.springsecuritysection6.repository.AccountTransactionsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

	private final AccountTransactionsRepository accountTransactionsRepository;

	public BalanceController(AccountTransactionsRepository accountTransactionsRepository) {
		this.accountTransactionsRepository = accountTransactionsRepository;
	}

	@PostMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestBody Customer customer) {
		return accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(customer.getId());

	}
}
