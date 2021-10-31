package org.thehive.hiveserver.error;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Throwable throwable, HttpServletRequest request) {
        var excRes = responseOf(throwable, request);
        return ResponseEntity.status(excRes.getStatus()).body(excRes);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException exception, HttpServletRequest request) {
        var excRes = responseOf(exception, request);
        return ResponseEntity.status(excRes.getStatus()).body(excRes);
    }

    private ExceptionResponse responseOf(@NonNull Throwable throwable, @NonNull HttpServletRequest request) {
        return ExceptionResponse.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .exception(throwable.getClass().getName())
                .message(throwable.getMessage())
                .path(request.getServletPath())
                .method(request.getMethod())
                .build();
    }

    private ExceptionResponse responseOf(@NonNull ResponseStatusException exception, @NonNull HttpServletRequest request) {
        return ExceptionResponse.builder()
                .timestamp(System.currentTimeMillis())
                .status(exception.getRawStatusCode())
                .error(exception.getStatus().getReasonPhrase())
                .exception(exception.getClass().getName())
                .message(exception.getMessage())
                .path(request.getServletPath())
                .method(request.getMethod())
                .build();
    }

}
