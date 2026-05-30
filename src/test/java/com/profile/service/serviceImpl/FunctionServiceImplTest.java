package com.profile.service.serviceImpl;

import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.FunctionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.relation.Role;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FunctionServiceImplTest {

    @Mock
    private FunctionServiceImpl mockFunctionService;

    @Mock
    private UserRepository mockUserRepository;

    private User mockedUser;

    @BeforeEach
    void setUp(){
        mockFunctionService= new FunctionServiceImpl(mockUserRepository);
        mockedUser=new User();
        mockedUser.setUsername("mockedUser");
        mockedUser.setBanned(false);
        mockedUser.setRole(RolesEnum.USER);
    }


    @Test
    void changeUserRole() {
        String user = "mockedUser";
        RolesEnum newRole = RolesEnum.ADMIN;
        RolesEnum currentRole = mockedUser.getRole();


        when(mockUserRepository.findByUsername(user)).thenReturn(Optional.of(mockedUser));

        mockFunctionService.changeUserRole(user,newRole);
        Assertions.assertEquals(RolesEnum.ADMIN, mockedUser.getRole());
        Assertions.assertNotEquals(currentRole,mockedUser.getRole());
    }

    @Test
    void changeInvalidUserRole() {
        String user = "invalidUser";
        RolesEnum newRole = RolesEnum.MODERATOR;

        when(mockUserRepository.findByUsername(user)).thenReturn(Optional.empty());
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                ()->mockFunctionService.changeUserRole(user,newRole)
        );

        Assertions.assertEquals("User not found!", exception.getMessage());
    }

    @Test
    void deleteUser() {
    }

    @Test
    void banUser() {
    }

    @Test
    void unbanUser() {
    }
}