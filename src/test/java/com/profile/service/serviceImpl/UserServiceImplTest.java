package com.profile.service.serviceImpl;

import com.profile.models.dto.adminAccessDTO.FunctionsDTO;
import com.profile.models.dto.adminAccessDTO.GetRegisteredUsersDTO;
import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
    private PasswordEncoder mockPasswordEncoder;
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
        registeredUser.setBanned(false);
        registeredUser.setRole(RolesEnum.USER);

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

        assertTrue(isUsernameAvailable);
        assertFalse(isUsernameTaken);
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
    void executeAdminActionInvalidUser() {
        FunctionsDTO functionsDTO = new FunctionsDTO();
        functionsDTO.setFunctionName("Unban user");
        functionsDTO.setUsername("invalidUser");
        boolean isBanned = false;

        when(mockUserRepository.findByUsername(functionsDTO.getUsername()))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> mockUserService.executeAdminAction(functionsDTO)
        );

        assertEquals("User not found!", exception.getMessage());
    }


    @Test
    void executeAdminActionChangeRoleTest() {

        FunctionsDTO functionsDTO = new FunctionsDTO();
        functionsDTO.setFunctionName("Change role");
        functionsDTO.setRole(RolesEnum.ADMIN);
        functionsDTO.setUsername(registeredUser.getUsername());

        when(mockUserRepository.findByUsername(functionsDTO.getUsername())).thenReturn(Optional.of(registeredUser));

        mockUserService.executeAdminAction(functionsDTO);

        Assertions.assertEquals(RolesEnum.ADMIN, registeredUser.getRole());
        verify(mockUserRepository).save(registeredUser);
    }

    @Test
    void executeAdminActionBanUser() {
        FunctionsDTO functionsDTO = new FunctionsDTO();
        functionsDTO.setFunctionName("Ban user");
        functionsDTO.setUsername(registeredUser.getUsername());
        boolean isBanned = true;

        when(mockUserRepository.findByUsername(functionsDTO.getUsername())).thenReturn(Optional.of(registeredUser));

        mockUserService.executeAdminAction(functionsDTO);

        assertTrue(registeredUser.isBanned());
        verify(mockBlackListService).addUserToBlackList(registeredUser);
        verify(mockUserRepository).save(registeredUser);
}

    @Test
    void executeAdminActionUnbanUser() {
        FunctionsDTO functionsDTO = new FunctionsDTO();
        functionsDTO.setFunctionName("Unban user");
        functionsDTO.setUsername(registeredUser.getUsername());
        boolean isBanned = false;

        when(mockUserRepository.findByUsername(functionsDTO.getUsername())).thenReturn(Optional.of(registeredUser));
        registeredUser.setBanned(true);

        mockUserService.executeAdminAction(functionsDTO);

        assertFalse(registeredUser.isBanned());

        verify(mockBlackListService).deleteUserFromBlackList(registeredUser);
        verify(mockUserRepository).save(registeredUser);
    }

    @Test
    void executeAdminActionDeleteUser() {

        FunctionsDTO functionsDTO = new FunctionsDTO();
        functionsDTO.setFunctionName("Delete user");
        functionsDTO.setUsername(registeredUser.getUsername());

        when(mockUserRepository.findByUsername(functionsDTO.getUsername())).thenReturn(Optional.of(registeredUser));

        mockUserService.executeAdminAction(functionsDTO);

        verify(mockUserRepository).delete(registeredUser);
    }

    @Test
    void getAllUsers() {

        List<GetRegisteredUsersDTO> allRegisteredUsers = new ArrayList<>();
        allRegisteredUsers.add("1", "Goshko",)

        when(mockUserRepository)
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
