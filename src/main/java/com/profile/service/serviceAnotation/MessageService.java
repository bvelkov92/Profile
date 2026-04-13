package com.profile.service.serviceAnotation;

import com.profile.models.dto.MessageDTO.LoggedUserMessagesDTO;

import java.util.List;

public interface MessageService {

    void sendMsg(String sender, Long receiverId,  String message);
    List<LoggedUserMessagesDTO> getAllMyMessages(String username);
    boolean hasUnreadMessages(String username);
}
