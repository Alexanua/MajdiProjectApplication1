package com.Majdi.Majdi_Project;



import com.Majdi.Majdi_Project.Entity.Message;
import com.Majdi.Majdi_Project.Repository.MessageRepository;
import com.Majdi.Majdi_Project.Service.MessageService;
import com.Majdi.Majdi_Project.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMessageById_found() {
        Long id = 1L;
        Message message = new Message(id, "Test message");
        when(messageRepository.findById(id)).thenReturn(Optional.of(message));

        Optional<Message> result = messageService.getMessageById(id);

        assertTrue(result.isPresent());
        assertEquals(message.getContent(), result.get().getContent());
    }

    @Test
    void getMessageById_notFound() {
        Long id = 2L;
        when(messageRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            messageService.getMessageById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));
        });

        String expectedMessage = "Message not found for this id :: " + id;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveMessage_success() {
        Message message = new Message(null, "New message");
        Message savedMessage = new Message(1L, "New message");
        when(messageRepository.save(any(Message.class))).thenReturn(savedMessage);

        Message result = messageService.saveMessage(message);

        assertNotNull(result.getId());
        assertEquals(savedMessage.getContent(), result.getContent());
    }

    @Test
    void getAllMessages_success() {
        List<Message> messages = Arrays.asList(new Message(1L, "Hello World"), new Message(2L, "Hello Spring"));
        when(messageRepository.findAll()).thenReturn(messages);

        List<Message> result = messageService.getAllMessages();

        assertEquals(2, result.size());
        assertEquals(messages.get(0).getContent(), result.get(0).getContent());
        assertEquals(messages.get(1).getContent(), result.get(1).getContent());
    }

    @Test
    void deleteMessage_success() {
        Long id = 1L;
        Message message = new Message(id, "Hello World");
        when(messageRepository.findById(id)).thenReturn(Optional.of(message));
        doNothing().when(messageRepository).delete(message);

        assertDoesNotThrow(() -> messageService.deleteMessage(id));
        verify(messageRepository, times(1)).delete(message);
    }
}
