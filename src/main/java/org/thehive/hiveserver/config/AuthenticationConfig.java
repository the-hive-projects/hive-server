package org.thehive.hiveserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.thehive.hiveserver.security.SecurityUserDetailsService;
import org.thehive.hiveserver.security.ExceptionResponseAuthenticationEntryPoint;
import org.thehive.hiveserver.service.UserService;

@Configuration
public class AuthenticationConfig {

    @Profile("dev")
    @Bean(name = "passwordEncoder")
    public PasswordEncoder devPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new SecurityUserDetailsService(userService);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return new ExceptionResponseAuthenticationEntryPoint(objectMapper);
    }

}
