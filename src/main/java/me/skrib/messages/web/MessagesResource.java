package me.skrib.messages.web;

import me.skrib.messages.model.Geolocation;
import me.skrib.messages.model.Message;
import me.skrib.messages.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesResource {

    private final MessageService messageService;

    public MessagesResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Void> saveMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages(
            @RequestHeader(name = Geolocation.HEADER_VALUE) Geolocation userGeolocation) {
        return ResponseEntity.ok(messageService.listReachableMessages(userGeolocation));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Message> getMessage(
            @RequestHeader(name = Geolocation.HEADER_VALUE) Geolocation userGeolocation,
            @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(messageService.getMessage(id, userGeolocation));
    }
}
