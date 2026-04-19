package com.profile.models.dto.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoggedUserMessagesDTO {

    private Long id;
    private String sender;
    private String receiver;
    private String text;
    private String subject;
    private String sentAt;
    private boolean isSeenByReceiver;

}
