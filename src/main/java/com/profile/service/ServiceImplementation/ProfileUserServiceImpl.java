package com.profile.service.ServiceImplementation;


import com.profile.models.entity.User;
import com.profile.repositiry.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public ProfileUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(()-> new UsernameNotFoundException("User " + username +" not found!"));
    }


    private UserDetails map(User user){
        return   org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ADMIN")
                .build();
    }
}
