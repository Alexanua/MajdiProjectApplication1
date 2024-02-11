package com.Majdi.Majdi_Project.controller;

import com.Majdi.Majdi_Project.Entity.Message;

import com.Majdi.Majdi_Project.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService service;

    // Hämta alla meddelanden
    @GetMapping("/get")
    public List<Message> getAllMessages() {
        return service.getAllMessages();
    }

    // Hämta ett meddelande med ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return service.getMessageById(id)
                .map(message -> ResponseEntity.ok().body(message))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Skapa ett nytt meddelande
    @PostMapping("/post")
    public Message createMessage(@RequestBody Message message) {
        return service.saveMessage(message);
    }

    // Uppdatera ett meddelande
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message messageDetails) {
        Message updatedMessage = service.updateMessage(id, messageDetails);
        return ResponseEntity.ok(updatedMessage);
    }

    // Ta bort ett meddelande
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable Long id) {
        service.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
/*
För att hämta alla meddelanden: Skapa en GET-förfrågan till http://localhost:8081/messages/get
För att hämta ett meddelande med ID 1: Skapa en GET-förfrågan till http://localhost:8081/messages/1
För att skapa ett nytt meddelande: Skapa en POST-förfrågan till http://localhost:8081/messages med en JSON i förfråganskroppen som representerar meddelandet.
För att uppdatera ett meddelande med ID 1: Skapa en PUT-förfrågan till http://localhost:8081/messages/1 med en JSON i förfråganskroppen som representerar de uppdaterade meddelandedetaljerna.
För att radera ett meddelande med ID 1: Skapa en DELETE-förfrågan till http://localhost:8081/messages/1
 */

