package org.thehive.hiveserver.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.thehive.hiveserver.session.LiveSessionHolder;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@Endpoint(id = "live-session-list")
public class LiveSessionListEndpoint {

    private final LiveSessionHolder liveSessionHolder;

    @ReadOperation
    public Collection<String> list() {
        return liveSessionHolder.allIds();
    }

}
