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

    private String text;

    private LocalDateTime sentAt;

    private String subject;

    private boolean isSeenFromSender;

    private boolean isSeenFromReceiver;

}
