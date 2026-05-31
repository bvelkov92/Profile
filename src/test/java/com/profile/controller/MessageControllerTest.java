package com.profile.controller;

import com.profile.service.serviceAnotation.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService mockMessageService;

    @Test
    void getSendMessagePage() throws Exception {

        mockMvc.perform(get("/profile/1/send"))
                .andExpect(status().isOk())
                .andExpect(view().name("message-form"))
                .andExpect(model().attributeExists("messageDTO"))
                .andExpect(model().attributeExists("id"));
    }

    @Test
    void postSendMessagePage() throws Exception {

        mockMvc.perform(post("/profile/1/send")
                        .principal(() -> "pesho")
                        .param("text", "hello")
                        .param("subject", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/1"));

        verify(mockMessageService)
                .sendMsg("pesho", 1L, "hello", "test");
    }

    @Test
    void getMyMessagesPage() throws Exception {

        mockMvc.perform(get("/profile/messages"))
                .andExpect(status().isOk())
                .andExpect(view().name("messages"));
    }

    @Test
    void deleteSelectedMessage() throws Exception {

        mockMvc.perform(post("/profile/messages/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/messages"));

        verify(mockMessageService).deleteMessage(1L);
    }

    @Test
    void getContactPage() throws Exception {

        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"))
                .andExpect(model().attributeExists("sendMessageToAllAdminsDTO"));
    }

        @Test
        void postSendMessageToAllAdmins() throws Exception {

            mockMvc.perform(post("/contact")
                            .param("name", "Boris")
                            .param("email", "boris@test.com")
                            .param("subject", "test")
                            .param("text", "message"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));

            verify(mockMessageService)
                    .sendMsgToAllAdmins(any());
        }

    @Test
    void postChangeMessageStatus() throws Exception {

        mockMvc.perform(post("/profile/messages/check/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/messages"));

        verify(mockMessageService)
                .changeMessageStatus(1L);
    }

    @Test
    void getViewMessagePage() throws Exception {

        mockMvc.perform(get("/profile/messages/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("message-view"))
                .andExpect(model().attributeExists("viewMessageDTO"));
    }
}