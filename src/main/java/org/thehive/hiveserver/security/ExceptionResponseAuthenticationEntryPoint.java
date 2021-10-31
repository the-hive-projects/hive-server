package org.thehive.hiveserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.thehive.hiveserver.error.ExceptionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionResponseAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        var exceptionResponse = ExceptionResponse.of(e, httpServletRequest, HttpStatus.UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        objectMapper.writeValue(httpServletResponse.getWriter(), exceptionResponse);
    }

}