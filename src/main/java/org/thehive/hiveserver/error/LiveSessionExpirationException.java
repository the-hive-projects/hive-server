package org.thehive.hiveserver.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LiveSessionExpirationException extends ResponseStatusException {

    public LiveSessionExpirationException() {
        super(HttpStatus.BAD_REQUEST, "Session has been expired");
    }

}
