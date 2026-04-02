package com.profile.models;

import com.profile.repositiry.UserRepository;
import com.profile.service.ServiceImplementation.ProfileUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfigurator {




    @Bean
    public UserDetailsService userDetailsService (UserRepository userRepository){
        return new ProfileUserServiceImpl(userRepository);
    }
}
