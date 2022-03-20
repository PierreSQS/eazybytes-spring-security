package com.eazybytes.springsecuritysection2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @GetMapping("/myLoans")
    public String getLoanDetails(String input) {
        return "Here are the loan details from the DB";
    }
}
