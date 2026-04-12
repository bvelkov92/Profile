package com.profile.service.serviceAnotation;

public interface MessageService {

    void sendMsg(String sender, Long receiverId,  String message);
}
