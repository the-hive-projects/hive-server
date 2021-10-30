package org.thehive.hiveserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.service.UserService;

@Configuration
public class SecurityConfig {

    @Profile("dev")
    @Bean(name = "passwordEncoder")
    public PasswordEncoder devPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new SimpleUserDetailsService(userService);
    }

    @RequiredArgsConstructor
    public static class SimpleUserDetailsService implements UserDetailsService {

        private final UserService userService;

        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            return userDetailsOf(userService.findByUsername(s));
        }

        private UserDetails userDetailsOf(User user) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        }

    }

}
