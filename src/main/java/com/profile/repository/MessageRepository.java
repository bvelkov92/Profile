package com.profile.repository;

import com.profile.models.entity.Message;
import com.profile.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllBySenderOrReceiver(User sender, User receiver);
    List<Message> findAllByReceiver(User receiver);
}
