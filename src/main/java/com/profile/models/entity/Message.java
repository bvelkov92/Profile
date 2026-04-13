package com.profile.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message extends BaseEntity{

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private LocalDateTime sentAt;

    @Column
    private boolean isSeenForSender;

    @Column
    private boolean isSeenForReceiver;

}
