package org.thehive.hiveserver.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;
    private String method;

    public static ExceptionResponse of(@NonNull Throwable throwable, @NonNull HttpServletRequest request) {
        return throwable instanceof ResponseStatusException ?
                of(throwable, request, ((ResponseStatusException) throwable).getStatus()) :
                of(throwable, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ExceptionResponse of(@NonNull Throwable throwable, @NonNull HttpServletRequest request, @NonNull HttpStatus status) {
        return ExceptionResponse.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error(status.getReasonPhrase())
                .exception(throwable.getClass().getName())
                .message(throwable.getMessage())
                .path(request.getServletPath())
                .method(request.getMethod())
                .build();
    }

}
