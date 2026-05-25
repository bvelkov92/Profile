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

    Message message = new Message();
    User foundSender = new User();
    User foundReceiver = new User();

    @BeforeEach
    void setUp(){

        ///======= SERVICE =======
        mockMessageService = new MessageServiceImpl(mockUserRepository,
                mockMessageRepository,
                mockModelMapper);

        //============= USERS ======================

        foundSender.setUsername("mockSender");
        foundSender.setSentMessages(new ArrayList<>());
        foundSender.setReceivedMessages(new ArrayList<>());

        foundReceiver.setUsername("mockReceiver");
        foundReceiver.setSentMessages(new ArrayList<>());
        foundReceiver.setReceivedMessages(new ArrayList<>());

       ///============= Messages ===================================

        message.setSender(foundSender);
        message.setReceiver(foundReceiver);
        message.setText("Text message");
        message.setSubject("Subject");
        message.setSentAt(LocalDateTime.now());
        message.setSeenFromReceiver(false);
        message.setSeenFromSender(true);

    }

    @Test
    void sendMsg() {
        String senderUsername = "mockSender";
        Long receiverById = 2L;
        String testMessage = "Text message";
        String subject = "Subject";

        foundSender.getSentMessages().add(message);
        foundReceiver.getReceivedMessages().add(message);

        when(mockUserRepository.findByUsername(senderUsername)).thenReturn(Optional.of(foundSender));
        when(mockUserRepository.findById(receiverById)).thenReturn(Optional.of(foundReceiver));
        when(mockModelMapper.map(any(MessageDTO.class), eq(Message.class)))
                .thenReturn(message);

        mockMessageService.sendMsg(senderUsername, receiverById,testMessage,subject);

        Integer senderNumberReceivedMessagesMustBeZero = foundSender.getReceivedMessages().size();
        Integer senderNumberSentMessagesMustBeOne = foundSender.getSentMessages().size();

        Integer receiverNumberSentMessagesMustBeZero = foundReceiver.getSentMessages().size();
        Integer receiverNumberReceivedMessagesMustBeOne = foundReceiver.getReceivedMessages().size();

        Assertions.assertEquals(1, senderNumberSentMessagesMustBeOne);
        Assertions.assertEquals(0, senderNumberReceivedMessagesMustBeZero);

        Assertions.assertEquals(1, receiverNumberReceivedMessagesMustBeOne);
        Assertions.assertEquals(0, receiverNumberSentMessagesMustBeZero);

        Assertions.assertEquals("Text message", foundSender.getSentMessages().getFirst().getText());
        Assertions.assertEquals("Subject", foundSender.getSentMessages().getFirst().getSubject());
        Assertions.assertEquals("Text message", foundReceiver.getReceivedMessages().getFirst().getText());
        Assertions.assertEquals("Subject", foundReceiver.getReceivedMessages().getFirst().getSubject());
    }

    @Test
    void getAllMyMessages() {
        Authentication authentication =mock(Authentication.class);
        SecurityContext securityContext= mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        foundSender.getSentMessages().add(message);

        List<Message> listWithMessages = List.of(message);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("loggedUser");
        when(mockUserRepository.findByUsername("loggedUser")).thenReturn(Optional.of(foundSender));

        when(mockMessageRepository.findAllBySenderOrReceiver(foundSender,foundSender))
                .thenReturn(listWithMessages);

        List<LoggedUserMessagesDTO> allMyMessages = mockMessageService.getAllMyMessages();

        Assertions.assertEquals(1, allMyMessages.size());
        Assertions.assertEquals("Subject", allMyMessages.getFirst().getSubject());
        Assertions.assertEquals("Text message", allMyMessages.getFirst().getText());

    }

    @Test
    void hasUnreadMessages() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        foundReceiver.getReceivedMessages().add(message);

        List<Message> receivedMessages = List.of(message);

        when(authentication.getName()).thenReturn("mockReceiver");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(mockUserRepository.findByUsername(authentication.getName())).thenReturn(Optional.of(foundReceiver));
        when(mockMessageRepository.findAllBySenderOrReceiver(foundReceiver, foundReceiver)).thenReturn(receivedMessages);

        boolean hasUnreadMessages = mockMessageService.hasUnreadMessages();
        Assertions.assertTrue(hasUnreadMessages);

        receivedMessages.getFirst().setSeenFromReceiver(true);
        boolean hasNotUnreadMessages = mockMessageService.hasUnreadMessages();
        Assertions.assertFalse(hasNotUnreadMessages);
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
