package me.skrib.messages.model;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by damien on 09/01/2017.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
}
