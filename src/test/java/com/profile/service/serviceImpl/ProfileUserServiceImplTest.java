package com.profile.service.serviceImpl;

import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileUserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    private ProfileUserServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProfileUserServiceImpl(mockUserRepository);
    }

    @Test
    void loadUserByUsername_Success() {
        User user = new User();
        user.setUsername("pesho");
        user.setPassword("123");
        user.setRole(RolesEnum.ADMIN);
        user.setBanned(false);

        when(mockUserRepository.findByUsername("pesho"))
                .thenReturn(Optional.of(user));

        UserDetails result = service.loadUserByUsername("pesho");

        assertEquals("pesho", result.getUsername());
        assertEquals("123", result.getPassword());
        assertTrue(result.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(result.isAccountNonLocked());
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        when(mockUserRepository.findByUsername("missing"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("missing")
        );
    }
}