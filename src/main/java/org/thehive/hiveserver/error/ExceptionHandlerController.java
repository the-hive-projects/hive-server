package org.thehive.hiveserver.error;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.thehive.hiveserver.validation.ValidationErrorMessageFormatter;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerController {

    private final ValidationErrorMessageFormatter validationErrorMessageFormatter;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionResponse> handleException(Throwable throwable, HttpServletRequest request) {
        var response = ExceptionResponse.of(throwable, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException exception, HttpServletRequest request) {
        var response = ExceptionResponse.of(exception, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception, HttpServletRequest request) {
        var response = ExceptionResponse.of(exception, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponse> handleBindException(BindException exception, HttpServletRequest request) {
        var response = ExceptionResponse.of(exception, request, HttpStatus.BAD_REQUEST);
        response.setMessage(validationErrorMessageFormatter.format(exception.getBindingResult()));
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
