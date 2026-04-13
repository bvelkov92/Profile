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
    private String sentAt;


    public String getShortText() {
        return text.length() <= 20 ? text : text.substring(0, 20) + "...";
    }
}
