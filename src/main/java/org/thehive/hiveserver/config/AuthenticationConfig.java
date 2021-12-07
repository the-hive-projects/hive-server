package org.thehive.hiveserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.thehive.hiveserver.security.ExceptionResponseAuthenticationEntryPoint;
import org.thehive.hiveserver.security.SecurityUserDetailsService;
import org.thehive.hiveserver.security.UrlEndpointWhitelist;
import org.thehive.hiveserver.service.UserService;

@Configuration
public class AuthenticationConfig {

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new SecurityUserDetailsService(userService);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return new ExceptionResponseAuthenticationEntryPoint(objectMapper);
    }

    @Profile("dev")
    @Bean(name = "passwordEncoder")
    public PasswordEncoder devPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Profile("pro")
    @Bean(name = "passwordEncoder")
    public PasswordEncoder proPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Profile("dev")
    @Bean(name = "urlEndpointWhitelist")
    public UrlEndpointWhitelist devUrlEndpointWhitelist() {
        return new UrlEndpointWhitelist(
                new String[]{
                        //actuator endpoints
                        "/actuator",
                        "/actuator/**",
                        //swagger endpoints
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        // h2 console endpoints
                        "/h2-console",
                        "/h2-console/**"
                },
                new String[]{
                        // h2 console endpoints
                        "/h2-console",
                        "/h2-console/**",
                });
    }

    @Profile("pro")
    @Bean(name = "urlEndpointWhitelist")
    public UrlEndpointWhitelist proUrlEndpointWhitelist() {
        return new UrlEndpointWhitelist(new String[]{}, new String[]{});
    }

}
