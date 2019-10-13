package me.skrib.messages.web;

import me.skrib.messages.model.Geolocation;
import me.skrib.messages.model.Message;
import me.skrib.messages.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("messages")
public class MessagesResource {

    private final MessageService messageService;

    public MessagesResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> saveMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages(
            @RequestParam(name = "latitude") double latitude,
            @RequestParam(name = "longitude") double longitude) {
        Geolocation geolocation = Geolocation.location().latitude(latitude).longitude(longitude).build();
        return ResponseEntity.ok(messageService.listReachableMessages(geolocation));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Message> getMessage(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "latitude") double latitude,
            @RequestParam(name = "longitude") double longitude) {
        Geolocation geolocation = Geolocation.location().latitude(latitude).longitude(longitude).build();
        return ResponseEntity.ok(messageService.getMessage(id, geolocation));
    }
}
