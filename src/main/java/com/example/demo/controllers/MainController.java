package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Hero/Main page
    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    // Request form page
    @GetMapping("/request-form")
    public String requestForm() {
        return "request-form";
    }

    // Update inventory form page
    @GetMapping("/update-form")
    public String updateForm() {
        return "update-form";
    }

    // Request summary page
    @GetMapping("/request-summary")
    public String requestSummary() {
        return "request-summary";
    }

    // Dashboard page
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}