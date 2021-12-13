package org.thehive.hiveserver.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;
import org.thehive.hiveserver.session.LiveSession;
import org.thehive.hiveserver.session.LiveSessionManager;

@Component
@RequiredArgsConstructor
@Endpoint(id = "live-session-information")
public class LiveSessionInformationEndpoint {

    private final LiveSessionManager liveSessionManager;

    @ReadOperation
    public LiveSession liveSession(@Selector String joinId) {
        return liveSessionManager.getSession(joinId);
    }

}
