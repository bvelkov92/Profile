package com.profile.service.serviceAnotation;

import com.profile.models.dto.MessageDTO.LoggedUserMessagesDTO;
import com.profile.models.dto.userDTO.SendMessageToAllAdminsDTO;

import java.util.List;

public interface MessageService {

    void sendMsg(String sender, Long receiverId,  String message, String subject);
    List<LoggedUserMessagesDTO> getAllMyMessages(String username);
    boolean hasUnreadMessages(String username);
    void deleteMessage(Long id);
    void sendMsgToAllAdmins(SendMessageToAllAdminsDTO sendMessageToAllAdminsDTO);
    void changeMessageStatus(Long id);
}
