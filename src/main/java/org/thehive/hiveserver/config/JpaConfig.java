package org.thehive.hiveserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.security.SecurityUser;

import java.util.Optional;

@Configuration
@EnableJpaRepositories(basePackages = "org.thehive.hiveserver.repository")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {

    @Bean
    public AuditorAware<User> auditorAware() {
        return new UserAuditorAware();
    }

    public static class UserAuditorAware implements AuditorAware<User> {

        @Override
        public Optional<User> getCurrentAuditor() {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof AnonymousAuthenticationToken)
                return Optional.empty();
            var securityUser = (SecurityUser) authentication.getPrincipal();
            var user = new User();
            user.setId(securityUser.getId());
            return Optional.of(user);
        }

    }

}
