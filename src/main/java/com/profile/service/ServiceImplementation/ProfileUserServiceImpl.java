package com.profile.service.ServiceImplementation;

import com.profile.models.entity.User;
import com.profile.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public ProfileUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(this::map)
                .orElseThrow(()-> new UsernameNotFoundException("User " + username +" not found!"));
    }

    private UserDetails map(User user){
        return   org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .accountLocked(user.isBanned())
                .build();
    }
}
