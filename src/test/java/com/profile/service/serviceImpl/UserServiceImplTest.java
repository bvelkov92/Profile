package com.profile.service.serviceImpl;

import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.models.entity.User;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.BlackListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
public class UserServiceImplTest {
    @Mock
    private UserServiceImpl mockUserService;


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
        mockUserService = new UserServiceImpl(mockUserRepository,
                mockPasswordEncoder,
                mockBlackListService,
                mockModelMapper);

    }


    @Test
    void userRegister() {

    }

    @Test
    void isUsernameValid() {

        UserRegisterDTO registeredUser = new UserRegisterDTO();
        registeredUser.setUsername("registered");

        UserRegisterDTO notRegisteredUser = new UserRegisterDTO();
        notRegisteredUser.setUsername("unregistered");

        User foundUser = new User();
        foundUser.setUsername("registered");

        when(mockUserRepository.findByUsername(registeredUser.getUsername())).thenReturn(Optional.of(foundUser));
        when(mockUserRepository.findByUsername(notRegisteredUser.getUsername())).thenReturn(Optional.empty());

        boolean isUsernameAvailable = mockUserService.isUsernameValid(notRegisteredUser);
        boolean isUsernameTaken = mockUserService.isUsernameValid(registeredUser);

        Assertions.assertTrue(isUsernameAvailable);
        Assertions.assertFalse(isUsernameTaken);
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
