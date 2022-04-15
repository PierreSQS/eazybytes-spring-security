package com.eazybytes.controller;

import com.eazybytes.model.Cards;
import com.eazybytes.model.Customer;
import com.eazybytes.repository.CardsRepository;
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
