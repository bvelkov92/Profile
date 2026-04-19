package com.profile.models.dto.MessageDTO;

import com.profile.models.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageToAdminsDTO {

    private User senderName;
    private User receiver;
    private String senderEmail;
    private String message;
    private String subject;
    private LocalDateTime sentAt;
    private boolean isSeenFromSender;
    private boolean isSeenFromReceiver;

}
