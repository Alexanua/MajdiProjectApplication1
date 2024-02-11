package com.Majdi.Majdi_Project.Service;

import com.Majdi.Majdi_Project.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;


import com.Majdi.Majdi_Project.Entity.Message;
import com.Majdi.Majdi_Project.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    // Hämta alla meddelanden
    public List<Message> getAllMessages() {
        return repository.findAll();
    }

    // Skapa ett nytt meddelande
    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    // Hämta ett meddelande med ID
    public Optional<Message> getMessageById(Long id) {
        return repository.findById(id);
    }

    // Uppdatera ett meddelande
    public Message updateMessage(Long id, Message messageDetails) {
        Message message = repository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Message not found for this id :: " + id));
        message.setContent(messageDetails.getContent());
        final Message updatedMessage = repository.save(message);
        return updatedMessage;
    }

    // Ta bort ett meddelande
    public void deleteMessage(Long id) {
        Message message = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));
        repository.delete(message);
    }
}
