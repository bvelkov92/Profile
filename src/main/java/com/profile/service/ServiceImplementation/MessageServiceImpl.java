package com.profile.service.ServiceImplementation;

import com.profile.models.entity.Message;
import com.profile.models.entity.User;
import com.profile.repository.MessageRepository;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {
        private final UserRepository userRepository;
        private final MessageRepository messageRepository;
        private final ModelMapper modelMapper;

    public MessageServiceImpl(UserRepository userRepository, MessageRepository messageRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void sendMsg(String senderByLogin, Long receiverById, String message) {

        User sender = this.userRepository.findByUsername(senderByLogin).orElse(null);
        User receiver = this.userRepository.findById(receiverById).orElse(null);
        Message newMessage = new Message();
        if (sender !=null){
            newMessage.setSender(sender);
        }
        if (receiver!=null){
            newMessage.setReceiver(receiver);
        }
        newMessage.setItSeen(false);
        newMessage.setText(message);
        newMessage.setSentAt(LocalDateTime.now());

        System.out.println("Check result");

        this.messageRepository.save(modelMapper.map(newMessage, Message.class));

        //TODO: To add message Icon in NAV and show unseen messages
    }
}
