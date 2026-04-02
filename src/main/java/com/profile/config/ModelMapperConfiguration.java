package com.profile.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ModelMapperConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

            httpSecurity.authorizeHttpRequests(
                    authorizeRequests -> authorizeRequests
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers("/").permitAll()
                            .anyRequest().authenticated()

            );

        return httpSecurity.build();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
