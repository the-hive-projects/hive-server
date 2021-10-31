package org.thehive.hiveserver.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;
    private String method;

}
