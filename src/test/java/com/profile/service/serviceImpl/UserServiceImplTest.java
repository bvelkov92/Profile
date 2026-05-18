package com.profile.service.serviceImpl;

import com.profile.models.dto.adminAccessDTO.FunctionsDTO;
import com.profile.models.dto.adminAccessDTO.GetRegisteredUsersDTO;
import com.profile.models.dto.userDTO.*;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        User user1 = new User();
        user1.setUsername("gosko");
        user1.setRole(RolesEnum.USER);

        User user2 = new User();
        user2.setUsername("gosko1");
        user2.setRole(RolesEnum.USER);

        User user3 = new User();
        user3.setUsername("gosko2");
        user3.setRole(RolesEnum.ADMIN);

        User user4 = new User();
        user4.setUsername("gosko3");
        user4.setRole(RolesEnum.MODERATOR);

        User notReg = new User();
        notReg.setUsername("NotRegister");
        notReg.setRole(RolesEnum.USER);

        when(mockUserRepository.findAll()).thenReturn(List.of(user1,user2,user3,user4,notReg));

        List<GetRegisteredUsersDTO> foundUsers = mockUserService.getAllUsers();

        Assertions.assertEquals(4,foundUsers.size());
        Assertions.assertEquals("gosko", foundUsers.getFirst().getUsername());
        Assertions.assertEquals("gosko3", foundUsers.getLast().getUsername());

    }

    @Test
    void viewAllRegisteredUsers() {
        User user1 = new User();
        user1.setUsername("Peshko");
        user1.setRole(RolesEnum.USER);

        User user2 = new User();
        user2.setUsername("Goshko");
        user2.setRole(RolesEnum.USER);

        User notReg = new User();
        notReg.setUsername("NotRegister");
        notReg.setRole(RolesEnum.USER);

        when(mockUserRepository.findAll()).thenReturn(List.of(user1,user2,registeredUser,notReg));

        List<AllUsersDTO> allUsersDTOS = mockUserService.viewAllRegisteredUsers();

        Assertions.assertEquals(3, allUsersDTOS.size());
        Assertions.assertEquals("registered", allUsersDTOS.getLast().getUsername());
        Assertions.assertEquals("Peshko", allUsersDTOS.getFirst().getUsername());


    }

    @Test
    void getUserById() {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(registeredUser));
        User foundUser = mockUserService.getUserById(1L);
        User notFoundUser = mockUserService.getUserById(2L);

        Assertions.assertEquals("registered", foundUser.getUsername());
        Assertions.assertNull(notFoundUser);
    }

    @Test
    void getProfileInfo() {

        MyProfileDTO dto = new MyProfileDTO();
        dto.setUsername("registered");

        when(mockUserRepository.findById(1L))
                .thenReturn(Optional.of(registeredUser));

        when(mockModelMapper.map(registeredUser, MyProfileDTO.class))
                .thenReturn(dto);

        MyProfileDTO profileInfo = mockUserService.getProfileInfo(1L);


        Assertions.assertEquals("registered", profileInfo.getUsername());

        NullPointerException exception = assertThrows( NullPointerException.class,
        () -> mockUserService.getProfileInfo(2L));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void changeMyPassword() {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication())
                .thenReturn(authentication);

        when(authentication.getName())
                .thenReturn("registered");

        when(mockUserRepository.findByUsername("registered")).thenReturn(Optional.of(registeredUser));

        ChangeMyPasswordDTO passwordsDTO = new ChangeMyPasswordDTO();
        passwordsDTO.setNewPassword("123456");
        String password = registeredUser.getPassword();

        mockUserService.changeMyPassword(passwordsDTO);

        boolean wasChangedPasswords = !mockPasswordEncoder.matches(password, registeredUser.getPassword());

        Assertions.assertEquals(mockPasswordEncoder.encode(passwordsDTO.getNewPassword()), registeredUser.getPassword());
        Assertions.assertTrue(wasChangedPasswords);

    }

    @Test
    void getFullDataOfLoggedUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        MyProfileDTO allInfoOfLoggedUser = new MyProfileDTO();
        allInfoOfLoggedUser.setUsername("registered");

        when(securityContext.getAuthentication())
                .thenReturn(authentication);

        when(authentication.getName())
                .thenReturn("registered");

        when(mockUserRepository.findByUsername("registered")).thenReturn(Optional.of(registeredUser));


        when(mockModelMapper.map(registeredUser, MyProfileDTO.class))
                .thenReturn(allInfoOfLoggedUser);

        MyProfileDTO result =
                mockUserService.getFullDataOfLoggedUser();

        Assertions.assertEquals(
                "registered",
                result.getUsername()
        );


    }

    @Test
    void changeUserInfo() {
        UserProfileDTO newDataDto = new UserProfileDTO();
        newDataDto.setUsername(registeredUser.getUsername());

        UserProfileDTO invalidUser = new UserProfileDTO();
        invalidUser.setUsername("Mitko");

        newDataDto.setFirstName("Admin");
        newDataDto.setLastName( "Adminski");
        newDataDto.setAge(20);

        String lastNameBeforeMethod = registeredUser.getLastName();
        Integer ageBeforeMethod = registeredUser.getAge();

        when(mockUserRepository.findByUsername(newDataDto.getUsername())).thenReturn(Optional.of(registeredUser));

        mockUserService.changeUserInfo(newDataDto);

        Assertions.assertEquals("Admin", registeredUser.getFirstName());
        Assertions.assertNotEquals(lastNameBeforeMethod, registeredUser.getLastName());
        Assertions.assertNotEquals(ageBeforeMethod, registeredUser.getAge());
        Assertions.assertEquals(newDataDto.getUsername(), registeredUser.getUsername());

        NullPointerException exception = assertThrows(NullPointerException.class,
                ()-> mockUserService.changeUserInfo(invalidUser));

        Assertions.assertEquals("User not found!", exception.getMessage());

    }
}
