package me.skrib.messages.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "messages")
@Builder(builderMethodName = "message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private int distance;

}
