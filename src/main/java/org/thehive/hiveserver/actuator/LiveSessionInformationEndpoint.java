package org.thehive.hiveserver.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;
import org.thehive.hiveserver.session.live.LiveSession;
import org.thehive.hiveserver.session.live.LiveSessionHolder;

@Component
@RequiredArgsConstructor
@Endpoint(id = "liveSessionInformation")
public class LiveSessionInformationEndpoint {

    private final LiveSessionHolder liveSessionHolder;

    @ReadOperation
    public LiveSession liveSession(@Selector String liveId) {
        return liveSessionHolder.getSession(liveId);
    }

}
