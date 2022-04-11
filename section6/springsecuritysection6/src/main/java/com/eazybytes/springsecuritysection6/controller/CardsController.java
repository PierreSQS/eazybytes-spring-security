package com.eazybytes.springsecuritysection6.controller;

import com.eazybytes.springsecuritysection6.model.Cards;
import com.eazybytes.springsecuritysection6.model.Customer;
import com.eazybytes.springsecuritysection6.repository.CardsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

	private final CardsRepository cardsRepository;

	public CardsController(CardsRepository cardsRepository) {
		this.cardsRepository = cardsRepository;
	}

	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestBody Customer customer) {
		return cardsRepository.findByCustomerId(customer.getId());
	}

}
