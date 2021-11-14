package org.thehive.hiveserver.security;

import lombok.NonNull;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    public static SecurityUser extractSecurityUser(@NonNull HttpServletRequest request) throws IllegalStateException {
        var auth = (Authentication) request.getUserPrincipal();
        if (!auth.isAuthenticated())
            throw new IllegalStateException("Unauthorized");
        return (SecurityUser) auth.getPrincipal();
    }

}
