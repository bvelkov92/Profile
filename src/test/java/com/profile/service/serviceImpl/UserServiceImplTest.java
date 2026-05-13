package com.profile.service.serviceImpl;

import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.BlackListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserServiceImpl userService;


    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private  PasswordEncoder mockPasswordEncoder;
    @Mock
    private BlackListService mockBlackListService;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(mockUserRepository,
                mockPasswordEncoder,
                mockBlackListService,
                mockModelMapper);

    }


    @Test
    void userRegister() {

    }

    @Test
    void isUsernameValid() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void executeAdminAction() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void viewAllRegisteredUsers() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getProfileInfo() {
    }

    @Test
    void changeMyPassword() {
    }

    @Test
    void getFullDataOfLoggedUser() {
    }

    @Test
    void changeUserInfo() {
    }
}
