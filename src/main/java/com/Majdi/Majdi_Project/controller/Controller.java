package com.Majdi.Majdi_Project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("welcome")
    public String hello() {
        return "Hello, World!";

        //http://localhost:8081/welcome
    }
}
