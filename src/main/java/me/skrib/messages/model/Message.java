package me.skrib.messages.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idMessage")
@Entity
public class Message {

    @Id
    private Long idMessage;
    private String body;
    @Embedded
    private Geolocation geolocation;
    private Long rayon;
    private Long authorId;

    /**
     * Distance is transient because we do not persist it as it depends on the user who is reading the message.
     * It is used for display only, to show how far is the message.
     */
    @Transient
    private double distance;

}
