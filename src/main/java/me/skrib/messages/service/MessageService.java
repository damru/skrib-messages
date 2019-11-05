package me.skrib.messages.service;

import me.skrib.messages.client.skrib.users.SkribUsersApi;
import me.skrib.messages.client.skrib.users.User;
import me.skrib.messages.exception.MessageTooFarException;
import me.skrib.messages.model.DistanceUnit;
import me.skrib.messages.model.Geolocation;
import me.skrib.messages.model.Message;
import me.skrib.messages.model.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final GeolocationService geolocationService;
    private final MessageRepository messageRepository;
    private final SkribUsersApi skribUsersApi;

    private final static int DISTANCE_MAX = 10000; // FIXME to be removed

    public MessageService(GeolocationService geolocationService,
                          MessageRepository messageRepository,
                          SkribUsersApi skribUsersApi) {
        this.geolocationService = geolocationService;
        this.messageRepository = messageRepository;
        this.skribUsersApi = skribUsersApi;
    }

    public Message saveMessage(Message message) {
        User author = skribUsersApi.me().getBody();
        message.setAuthorId(author.getId());
        message.setAuthor(author);
        return messageRepository.save(message);
    }

    public List<Message> listMessages(Geolocation userGeolocation) {
        List<Message> messages = messageRepository.findAllByOrderByIdDesc();
        return messages.stream()
                       .filter(message -> isReachable(message, userGeolocation, DISTANCE_MAX))
                       .peek(message -> {
                           User author = skribUsersApi.getByCriteria(Map.of("id", message.getAuthorId())).getBody();
                           message.setAuthor(author);
                       })
                       .collect(Collectors.toList());
    }

    public List<Message> listMessages(Geolocation userGeolocation, String username) {
        User author = skribUsersApi.getByCriteria(Map.of("username", username)).getBody();
        if (author != null) {
            List<Message> messages = messageRepository.findByAuthorIdOrderByIdDesc(author.getId());
            return messages.stream()
                           .filter(message -> isReachable(message, userGeolocation, DISTANCE_MAX))
                           .peek(message -> message.setAuthor(author))
                           .collect(Collectors.toList());
        }
        return null;
    }

    public Message getMessage(Long idMessage, Geolocation userGeolocation) {
        Message message = getMessage(idMessage);
        if (isReachable(message, userGeolocation, DISTANCE_MAX)) {
            User author = skribUsersApi.getByCriteria(Map.of("id", message.getAuthorId())).getBody();
            message.setAuthor(author);
            return message;
        }

        throw new MessageTooFarException("id", idMessage);
    }

    private Message getMessage(Long idMessage) {
        Optional<Message> message = messageRepository.findById(idMessage);

        if (!message.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id: " + idMessage);
        }
        return message.get();
    }

    /**
     * Check if a message is reachable by the current user.
     *
     * @param message
     * @param userGeolocation
     * @param distanceMax
     * @return
     */
    private boolean isReachable(Message message, Geolocation userGeolocation, int distanceMax) {
        int distanceBetweenUserAndMessage = (int)
                Math.round(geolocationService.distanceBetweenUserAndMessage(DistanceUnit.METER,
                                                                            userGeolocation,
                                                                            message.getGeolocation()));
        message.setDistance(distanceBetweenUserAndMessage);
        return distanceBetweenUserAndMessage <= message.getRayon() && distanceBetweenUserAndMessage <= distanceMax;
    }

}
