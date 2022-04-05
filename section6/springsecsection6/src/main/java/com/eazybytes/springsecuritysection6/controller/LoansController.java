package com.eazybytes.springsecuritysection6.controller;

import com.eazybytes.springsecuritysection6.model.Customer;
import com.eazybytes.springsecuritysection6.model.Loans;
import com.eazybytes.springsecuritysection6.repository.LoanRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {
	
	private final LoanRepository loanRepository;

	public LoansController(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@PostMapping("/myLoans")
	public List<Loans> getLoanDetails(@RequestBody Customer customer) {
		return loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());

	}

}
