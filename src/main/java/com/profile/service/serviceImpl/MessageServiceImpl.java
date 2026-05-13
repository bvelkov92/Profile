package com.profile.service.serviceImpl;

import com.profile.models.dto.MessageDTO.LoggedUserMessagesDTO;
import com.profile.models.dto.MessageDTO.MessageDTO;
import com.profile.models.dto.MessageDTO.MessageToAdminsDTO;
import com.profile.models.dto.userDTO.SendMessageToAllAdminsDTO;
import com.profile.models.entity.Message;
import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.repository.MessageRepository;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public void sendMsg(String senderByLogin, Long receiverById, String message, String subject) {

        User sender = this.userRepository.findByUsername(senderByLogin).orElse(null);
        User receiver = this.userRepository.findById(receiverById).orElse(null);
        MessageDTO newMessage = new MessageDTO();
        if (sender != null) {
            newMessage.setSender(sender);
        }
        if (receiver != null) {
            newMessage.setReceiver(receiver);
        }
        newMessage.setText(message);
        newMessage.setSentAt(LocalDateTime.now());
        newMessage.setSubject(subject);
        newMessage.setSeenFromSender(true);
        newMessage.setSeenFromReceiver(false);

        this.messageRepository.save(modelMapper.map(newMessage, Message.class));

    }

    @Override
    public List<LoggedUserMessagesDTO> getAllMyMessages() {
        Authentication username= SecurityContextHolder.getContext().getAuthentication();
        User senderAndReceiver = this.userRepository.findByUsername(username.getName()).orElse(null);

        if (senderAndReceiver!=null) {
            return this.messageRepository
                    .findAllBySenderOrReceiver(senderAndReceiver, senderAndReceiver)
                    .stream()
                    .map(message -> new LoggedUserMessagesDTO(
                            message.getId(),
                            message.getSender().getUsername(),
                            message.getReceiver().getUsername(),
                            message.getText(),
                            message.getSubject(),
                            message.getSentAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                            message.isSeenFromReceiver(),
                            message.isSeenFromSender()
                    )).toList().reversed();
        }
        return null;
    }

    @Override
    public boolean hasUnreadMessages() {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User foundUser = this.userRepository.findByUsername(loggedUser).orElse(null);
        return this.messageRepository.findAllBySenderOrReceiver(foundUser, foundUser)
                .stream()
                .filter(message -> message.getReceiver().getUsername().equals(loggedUser)
                        || message.getSender().getUsername().equals("NotRegister"))
                .anyMatch(message -> !message.isSeenFromReceiver());
    }

    @Override
    public void deleteMessage(Long id) {
        this.messageRepository.deleteById(id);
    }

    @Override
    public void sendMsgToAllAdmins(SendMessageToAllAdminsDTO sendMessageToAllAdminsDTO) {
        List<User> allAdmins = this.userRepository.findAllByRole(RolesEnum.ADMIN);

                if (this.userRepository.findByUsername("NotRegister").orElse(null)==null) {
                    User anonymousUser= new User();
                    anonymousUser.setUsername("NotRegister");
                    anonymousUser.setEmail("no-replay@borissite.com");
                    anonymousUser.setPassword("no_pass");
                    anonymousUser.setRole(RolesEnum.USER);
                    anonymousUser.setBanned(false);
                    this.userRepository.save(anonymousUser);
                }

        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userRepository.findByUsername(authUser.getName()).orElse(null);

        allAdmins.forEach(admin -> {
            MessageToAdminsDTO msg = new MessageToAdminsDTO();
            msg.setReceiver(admin.getUsername());
            //TODO: Да оптимизирам  метода.
            msg.setSubject(sendMessageToAllAdminsDTO.getSubject());
            msg.setSeenFromSender(true);
            msg.setSeenFromReceiver(false);
            msg.setSentAt(LocalDateTime.now());
            Message mappedMessage = this.modelMapper.map(msg, Message.class);
            mappedMessage.setText(sendMessageToAllAdminsDTO.getMessage());
            if (loggedUser!=null) {
                mappedMessage.setEmail(loggedUser.getEmail());
                mappedMessage.setSender(loggedUser);
                mappedMessage.setSenderName(loggedUser.getUsername());
            }else {
                User anonymousUser = this.userRepository.findByUsername("NotRegister").get();
                mappedMessage.setSender(anonymousUser);
                mappedMessage.setEmail(anonymousUser.getEmail());
                mappedMessage.setSenderName(sendMessageToAllAdminsDTO.getName());
            }
            mappedMessage.setReceiver(admin);
            messageRepository.save(mappedMessage);
            });
        }

    @Override
    public void changeMessageStatus(Long id) {
        Message message = this.messageRepository.findById(id).get();
        message.setSeenFromReceiver(!message.isSeenFromReceiver());
        this.messageRepository.save(message);
    }

}
