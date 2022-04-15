package com.eazybytes.controller;

import com.eazybytes.model.AccountTransactions;
import com.eazybytes.model.Customer;
import com.eazybytes.repository.AccountTransactionsRepository;
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
		return accountTransactionsRepository.
				findByCustomerIdOrderByTransactionDtDesc(customer.getId());

	}
}
