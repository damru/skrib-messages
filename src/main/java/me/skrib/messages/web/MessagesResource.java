package me.skrib.messages.web;

import me.skrib.messages.model.Geolocation;
import me.skrib.messages.model.Message;
import me.skrib.messages.service.MessageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessagesResource {

    private final MessageService messageService;

    public MessagesResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<Message>> listMessages(
            @RequestHeader(name = Geolocation.HEADER_VALUE + "-latitude") double latitude,
            @RequestHeader(name = Geolocation.HEADER_VALUE + "-longitude") double longitude) {
        Geolocation geolocation = Geolocation.location().latitude(latitude).longitude(longitude).build();
        return ResponseEntity.ok(messageService.listMessages(geolocation));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @PreAuthorize("#oauth2.hasScope('openid')")
    public ResponseEntity<Message> saveMessage(
            @RequestHeader(name = Geolocation.HEADER_VALUE + "-latitude") double latitude,
            @RequestHeader(name = Geolocation.HEADER_VALUE + "-longitude") double longitude,
            @RequestBody Message message) {
        Geolocation geolocation = Geolocation.location().latitude(latitude).longitude(longitude).build();
        message.setGeolocation(geolocation);
        return ResponseEntity.ok(messageService.saveMessage(message));
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Message> getMessage(
            @RequestHeader(name = Geolocation.HEADER_VALUE + "-latitude") double latitude,
            @RequestHeader(name = Geolocation.HEADER_VALUE + "-longitude") double longitude,
            @PathVariable(name = "id") Long id) {
        Geolocation geolocation = Geolocation.location().latitude(latitude).longitude(longitude).build();
        return ResponseEntity.ok(messageService.getMessage(id, geolocation));
    }

    @GetMapping(
            path = "/user/{username}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<Message>> listMessagesByUser(
            @RequestHeader(name = Geolocation.HEADER_VALUE + "-latitude") double latitude,
            @RequestHeader(name = Geolocation.HEADER_VALUE + "-longitude") double longitude,
            @PathVariable(name = "username") String username) {
        Geolocation geolocation = Geolocation.location().latitude(latitude).longitude(longitude).build();
        return ResponseEntity.ok(messageService.listMessages(geolocation, username));
    }

}
