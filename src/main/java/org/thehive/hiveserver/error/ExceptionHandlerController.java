package org.thehive.hiveserver.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Throwable throwable, HttpServletRequest request) {
        var response = ExceptionResponse.of(throwable, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException exception, HttpServletRequest request) {
        var response = ExceptionResponse.of(exception, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
