package com.profile.models.dto.MessageDTO;

import com.profile.models.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {

    private User sender;

    private User receiver;

    private String message;

    private LocalDateTime sentAt;

    private boolean itSeenForSender;

    private boolean itSeenFromReceiver;

}
