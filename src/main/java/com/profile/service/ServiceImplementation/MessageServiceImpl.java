package com.profile.service.ServiceImplementation;

import com.profile.models.dto.MessageDTO.LoggedUserMessagesDTO;
import com.profile.models.entity.Message;
import com.profile.models.entity.User;
import com.profile.repository.MessageRepository;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
        newMessage.setSeenForSender(true);
        newMessage.setSeenForReceiver(false);
        newMessage.setText(message);
        newMessage.setSentAt(LocalDateTime.now());

        this.messageRepository.save(modelMapper.map(newMessage, Message.class));
         }

    @Override
    public List<LoggedUserMessagesDTO> getAllMyMessages(String username) {
        User senderAndReceiver = this.userRepository.findByUsername(username).orElse(null);


        return this.messageRepository
                .findAllBySenderOrReceiver(senderAndReceiver, senderAndReceiver)
                .stream().map(message -> new LoggedUserMessagesDTO(
                        message.getId(),
                        message.getSender().getUsername(),
                        message.getReceiver().getUsername(),
                        message.getText(),
                        message.getSentAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                        message.isSeenForReceiver()
                )).toList();
    }

    @Override
    public boolean hasUnreadMessages(String username) {
        User foundUser = this.userRepository.findByUsername(username).orElse(null);
       return this.messageRepository.findAllByReceiver(foundUser)
                .stream().anyMatch(message -> !message.isSeenForReceiver());
    }

    @Override
    public void deleteMessage(Long id) {
        this.messageRepository.deleteById(id);
    }
}

