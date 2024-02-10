package com.Majdi.Majdi_Project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    private final List<String> messages = new ArrayList<>();

    // Constructor adds a default message
    public Controller() {
        messages.add("Hello, Abud");
    }

    // GET method to return all messages
    @GetMapping("/messages")
    public List<String> getMessages() {
        return messages;
    }

    // POST method to add a new message
    @PostMapping("/message")
    public String addMessage(@RequestBody String message) {
        messages.add(message);
        return "Message added successfully";
    }

    // Existing GET method for a simple welcome message
    @GetMapping("/welcome")
    public String hello() {
        return "Hello, this is a simple CRUD API";
    }
}
