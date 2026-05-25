package com.profile.service.serviceImpl;

import com.profile.models.dto.MessageDTO.LoggedUserMessagesDTO;
import com.profile.models.dto.MessageDTO.MessageDTO;
import com.profile.models.entity.Message;
import com.profile.models.entity.User;
import com.profile.repository.MessageRepository;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {
    @Mock
    private MessageService mockMessageService;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private MessageRepository mockMessageRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp(){
        mockMessageService = new MessageServiceImpl(mockUserRepository,
                mockMessageRepository,
                mockModelMapper);
    }

    @Test
    void sendMsg() {

        String senderUsername = "mockSender";
        Long receiverById = 2L;
        String message = "Successfully";
        String subject = "Test subject";


        User foundSender = new User();
        foundSender.setUsername("mockSender");
        foundSender.setSentMessages(new ArrayList<>());
        foundSender.setReceivedMessages(new ArrayList<>());

        User foundReceiver = new User();
        foundReceiver.setUsername("mockReceiver");
        foundReceiver.setSentMessages(new ArrayList<>());
        foundReceiver.setReceivedMessages(new ArrayList<>());

        Message mappedMessage = new Message();
        mappedMessage.setText(message);
        mappedMessage.setSentAt(LocalDateTime.now());
        mappedMessage.setSubject(subject);
        mappedMessage.setSeenFromSender(true);
        mappedMessage.setSeenFromReceiver(false);

        foundSender.getSentMessages().add(mappedMessage);
        foundReceiver.getReceivedMessages().add(mappedMessage);

        when(mockUserRepository.findByUsername(senderUsername)).thenReturn(Optional.of(foundSender));
        when(mockUserRepository.findById(receiverById)).thenReturn(Optional.of(foundReceiver));
        when(mockModelMapper.map(any(MessageDTO.class), eq(Message.class)))
                .thenReturn(mappedMessage);

        mockMessageService.sendMsg(senderUsername, receiverById,message,subject);

        Integer senderNumberReceivedMessagesMustBeZero = foundSender.getReceivedMessages().size();
        Integer senderNumberSentMessagesMustBeOne = foundSender.getSentMessages().size();

        Integer receiverNumberSentMessagesMustBeZero = foundReceiver.getSentMessages().size();
        Integer receiverNumberReceivedMessagesMustBeOne = foundReceiver.getReceivedMessages().size();

        Assertions.assertEquals(1, senderNumberSentMessagesMustBeOne);
        Assertions.assertEquals(0, senderNumberReceivedMessagesMustBeZero);

        Assertions.assertEquals(1, receiverNumberReceivedMessagesMustBeOne);
        Assertions.assertEquals(0, receiverNumberSentMessagesMustBeZero);

        Assertions.assertEquals("Successfully", foundSender.getSentMessages().getFirst().getText());
        Assertions.assertEquals("Test subject", foundSender.getSentMessages().getFirst().getSubject());
        Assertions.assertEquals("Successfully", foundReceiver.getReceivedMessages().getFirst().getText());
        Assertions.assertEquals("Test subject", foundReceiver.getReceivedMessages().getFirst().getSubject());
    }

    @Test
    void getAllMyMessages() {
        Authentication authentication =mock(Authentication.class);
        SecurityContext securityContext= mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        User loggedUser = new User();
        loggedUser.setUsername("loggedUser");
        loggedUser.setSentMessages(new ArrayList<>());
        loggedUser.setReceivedMessages(new ArrayList<>());

        User receiver = new User();
        receiver.setUsername("receiverUser");

        Message message = new Message();
        message.setSender(loggedUser);
        message.setReceiver(receiver);
        message.setText("Text message");
        message.setSubject("Subject");
        message.setSentAt(LocalDateTime.now());
        message.setSeenFromReceiver(false);
        message.setSeenFromSender(true);

        loggedUser.getSentMessages().add(message);

        List<Message> listWithMessages = List.of(message);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("loggedUser");
        when(mockUserRepository.findByUsername("loggedUser")).thenReturn(Optional.of(loggedUser));

        when(mockMessageRepository.findAllBySenderOrReceiver(loggedUser,loggedUser))
                .thenReturn(listWithMessages);

        List<LoggedUserMessagesDTO> allMyMessages = mockMessageService.getAllMyMessages();

        Assertions.assertEquals(1, allMyMessages.size());
        Assertions.assertEquals("Subject", allMyMessages.getFirst().getSubject());
        Assertions.assertEquals("Text message", allMyMessages.getFirst().getText());

    }

    @Test
    void hasUnreadMessages() {


    }

    @Test
    void deleteMessage() {
    }

    @Test
    void sendMsgToAllAdmins() {
    }

    @Test
    void changeMessageStatus() {
    }
}
