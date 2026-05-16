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

    private User registeredUser;

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

        registeredUser = new User();
        registeredUser.setUsername("registered");

    }

    @Test
    void userRegister() {

    }

    @Test
    void isUsernameValid() {

        UserRegisterDTO registeredUserDto = new UserRegisterDTO();
        registeredUserDto.setUsername("registered");

        UserRegisterDTO notRegisteredUserDto = new UserRegisterDTO();
        notRegisteredUserDto.setUsername("unregistered");

        when(mockUserRepository.findByUsername(registeredUserDto.getUsername())).thenReturn(Optional.of(registeredUser));
        when(mockUserRepository.findByUsername(notRegisteredUserDto.getUsername())).thenReturn(Optional.empty());

        boolean isUsernameAvailable = mockUserService.isUsernameValid(notRegisteredUserDto);
        boolean isUsernameTaken = mockUserService.isUsernameValid(registeredUserDto);

        Assertions.assertTrue(isUsernameAvailable);
        Assertions.assertFalse(isUsernameTaken);
    }

    @Test
    void getUserByUsername() {

        User findUser = new User();
        findUser.setUsername("registered");
        when(mockUserRepository.findByUsername(findUser.getUsername())).thenReturn(Optional.of(registeredUser));

        User foundUser = mockUserService.getUserByUsername(findUser.getUsername());
        User notFoundUser = mockUserService.getUserByUsername("notregistered");

        Assertions.assertEquals(registeredUser, foundUser);
        Assertions.assertNull(notFoundUser);
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
