package com.profile.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message extends BaseEntity{

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String text;

    private LocalDateTime sentAt;
    private boolean itSeen;

}
