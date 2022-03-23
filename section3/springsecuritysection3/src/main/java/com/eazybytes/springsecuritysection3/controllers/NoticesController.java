package com.eazybytes.springsecuritysection3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {

    @GetMapping("/notices")
    public String getNotices(String input) {
        return "Here are the notice details from the DB";
    }
}
