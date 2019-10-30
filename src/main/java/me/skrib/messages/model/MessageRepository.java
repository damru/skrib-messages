package me.skrib.messages.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByIdDesc();
    List<Message> findByAuthorIdOrderByIdDesc(Long authorId);
}
