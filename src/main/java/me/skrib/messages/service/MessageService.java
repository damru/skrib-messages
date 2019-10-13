package me.skrib.messages.service;

import me.skrib.messages.model.DistanceUnit;
import me.skrib.messages.model.Geolocation;
import me.skrib.messages.model.Message;
import me.skrib.messages.model.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by damien on 24/12/2016.
 */
@Service
public class MessageService {

    private final GeolocationService geolocationService;
    private final MessageRepository messageRepository;

    private final static int DISTANCE_MAX = 10000; // FIXME to be removed

    public MessageService(GeolocationService geolocationService, MessageRepository messageRepository) {
        this.geolocationService = geolocationService;
        this.messageRepository = messageRepository;
    }

    /**
     * Save message in database.
     *
     * @param message
     * @return new message
     */
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Get a list of all reachable messages depending on user location.
     *
     * @return message
     */
    public List<Message> listReachableMessages(Geolocation userGeolocation) {
        List<Message> messages = messageRepository.findAll();
        return messages.stream()
                       .filter(message -> isReachable(message, userGeolocation, DISTANCE_MAX))
                       .collect(Collectors.toList());
    }

    /**
     * Get a message depending on user location.
     *
     * @return message
     */
    public Message getMessage(Long idMessage, Geolocation userGeolocation) {
        Message message = getMessage(idMessage);
        if (isReachable(message, userGeolocation, DISTANCE_MAX)) {
            return message;
        }

        throw new ResponseStatusException(org.springframework.http.HttpStatus.FORBIDDEN, "Message if too far.");
    }

    /**
     * Get a message.
     *
     * @param idMessage
     * @return message
     */
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
