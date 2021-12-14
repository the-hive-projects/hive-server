package org.thehive.hiveserver.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;
import org.thehive.hiveserver.session.LiveSession;
import org.thehive.hiveserver.session.LiveSessionHolder;

@Component
@RequiredArgsConstructor
@Endpoint(id = "live-session-information")
public class LiveSessionInformationEndpoint {

    private final LiveSessionHolder liveSessionHolder;

    @ReadOperation
    public LiveSession liveSession(@Selector String joinId) {
        return liveSessionHolder.getSession(joinId);
    }

}
