package org.thehive.hiveserver.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.service.UserService;

@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return appUserOf(userService.findByUsername(s));
    }

    private SecurityUser appUserOf(User user) {
        return new SecurityUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

}