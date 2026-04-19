package com.profile.models.dto.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoggedUserMessagesDTO {

    private Long id;
    private String sender;
    private String receiver;
    private String text;
    private String subject;
    private String sentAt;
    private boolean isSeenByReceiver;
    private boolean isSeenBySender;

    public String getShortSubject() {
        if (subject == null) return "";
        return subject.length() > 20
                ? subject.substring(0, 10) + "..."
                : subject;
    }

}
