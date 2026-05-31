package com.profile.controller;

import com.profile.service.serviceAnotation.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void getRegisterPage() {

        
    }

    @Test
    void postRegisterPage() {
    }

    @Test
    void getWorkPage() {
    }

    @Test
    void getProfileInfoPage() {
    }

    @Test
    void getChangeProfileInfoPage() {
    }

    @Test
    void postProfileInfoPage() {
    }

    @Test
    void getSelectedProfilePage() {
    }

    @Test
    void getChangeMyPasswordPage() {
    }

    @Test
    void postChangeMyPasswordPage() {
    }
}