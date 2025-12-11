package com.wizardcloud.wizardbank.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WizardBankController {
    @Value("${spring.application.name}")
    private String app_name;

    @RequestMapping("/")
    public String index() {
        return "Welcome to " + app_name + "!";
    }
}
