package com.Majdi.Majdi_Project.service;

import com.Majdi.Majdi_Project.Entity.Message;
import com.Majdi.Majdi_Project.exception.ResourceNotFoundException;
import com.Majdi.Majdi_Project.Repository.MessageRepository;
import com.Majdi.Majdi_Project.Service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnMessageWhenMessageIdExists() {
        Long messageId = 1L;
        Message message = new Message(messageId, "Hello World");
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        Optional<Message> foundMessage = messageService.getMessageById(messageId);

        assertTrue(foundMessage.isPresent(), "Message should be found");
        assertEquals(message.getContent(), foundMessage.get().getContent(), "Message content should match");
    }

    @Test
    void shouldSaveNewMessage() {
        Message message = new Message(null, "Hello World");
        Message savedMessage = new Message(1L, "Hello World");
        when(messageRepository.save(message)).thenReturn(savedMessage);

        Message result = messageService.saveMessage(message);

        assertNotNull(result.getId(), "Saved message should have a non-null ID");
        assertEquals(savedMessage.getContent(), result.getContent(), "Message content should match");
    }

    @Test
    void shouldUpdateExistingMessage() {
        Long messageId = 1L;
        Message existingMessage = new Message(messageId, "Hello");
        Message newMessageDetails = new Message(messageId, "Hello World Updated");
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(existingMessage));
        when(messageRepository.save(existingMessage)).thenReturn(newMessageDetails);

        Message updatedMessage = messageService.updateMessage(messageId, newMessageDetails);

        assertEquals(newMessageDetails.getContent(), updatedMessage.getContent(), "Message content should be updated");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingMessage() {
        Long messageId = 1L;
        Message newMessageDetails = new Message(messageId, "Hello World Updated");
        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            messageService.updateMessage(messageId, newMessageDetails);
        });
    }

    @Test
    void shouldDeleteExistingMessage() {
        Long messageId = 1L;
        Message existingMessage = new Message(messageId, "Hello");
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(existingMessage));
        doNothing().when(messageRepository).delete(existingMessage);

        assertDoesNotThrow(() -> messageService.deleteMessage(messageId));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingMessage() {
        Long messageId = 1L;
        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            messageService.deleteMessage(messageId);
        });
    }

    @Test
    void shouldReturnAllMessages() {
        List<Message> messages = Arrays.asList(
                new Message(1L, "Hello World"),
                new Message(2L, "Hello Spring")
        );
        when(messageRepository.findAll()).thenReturn(messages);

        List<Message> result = messageService.getAllMessages();

        assertEquals(2, result.size(), "Should return all messages");
        assertEquals(messages.get(0).getContent(), result.get(0).getContent(), "First message content should match");
        assertEquals(messages.get(1).getContent(), result.get(1).getContent(), "Second message content should match");
    }
}
