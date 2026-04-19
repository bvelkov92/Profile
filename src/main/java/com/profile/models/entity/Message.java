package com.profile.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column()
    @NotBlank
    private String subject;

    @Column
    private LocalDateTime sentAt;

    @Column
    private boolean isSeenFromSender;

    @Column
    private boolean isSeenFromReceiver;

    private  String email;

}
