package com.foodapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Food Application Backend is Running!";
    }

    @GetMapping("/api/test")
    public String test() {
        return "API Working Successfully!";
    }
}