package org.thehive.hiveserver.security;

import lombok.NonNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public class SecurityUtils {

    public static SecurityUser extractSecurityUser(@NonNull HttpServletRequest request) throws IllegalStateException {
        return extractSecurityUser(request.getUserPrincipal());
    }

    public static SecurityUser extractSecurityUser(@NonNull ServerHttpRequest request) throws IllegalStateException {
        return extractSecurityUser(request.getPrincipal());
    }

    public static SecurityUser extractSecurityUser(@NonNull Principal principal) throws IllegalStateException {
        var authentication=(Authentication)principal;
        if (!authentication.isAuthenticated())
            throw new IllegalStateException("Authentication is unauthorized");
        return (SecurityUser) authentication.getPrincipal();
    }


}
