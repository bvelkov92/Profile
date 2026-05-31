package com.profile.controller;

import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.service.serviceAnotation.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;


    @Test
    @WithMockUser
    void getAllRegisteredUsersPage() throws Exception {

        when(mockUserService.viewAllRegisteredUsers())
                .thenReturn(List.of());

        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("all-users"))
                .andExpect(model().attributeExists("allUsers"));
    }

    @Test
    void getIndexPage() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithAnonymousUser
    void getLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("userLoginDTO"));
    }

    @Test
    @WithAnonymousUser
    void getRegisterPage() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterDTO"));

    }

    @Test
    void postRegisterPage() throws Exception {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("username");
        when(mockUserService.isUsernameValid(userRegisterDTO)).thenReturn(true);

        MockMultipartFile file =
                new MockMultipartFile(
                        "userImage",
                        "test.jpg",
                        "image/jpeg",
                        "test".getBytes());

        mockMvc.perform(multipart("/register")
                        .file(file)
                        .param("username", "username123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }
    @Test
    void postRegisterPageInvalidUsername() throws Exception {

        when(mockUserService.isUsernameValid(any()))
                .thenReturn(false);

        MockMultipartFile file =
                new MockMultipartFile(
                        "userImage",
                        "",
                        "image/jpeg",
                        new byte[0]);

        mockMvc.perform(multipart("/register")
                        .file(file)
                        .param("username", "username123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));

        verify(mockUserService, never()).userRegister(any(), any());
    }
}