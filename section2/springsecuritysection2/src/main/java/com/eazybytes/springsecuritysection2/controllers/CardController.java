package com.eazybytes.springsecuritysection2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    @GetMapping("/myCard")
    public String getCardDetails(String input) {
        return "Here are the Card details from the DB";
    }
}
