package org.thehive.hiveserver.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.thehive.hiveserver.websocket.authentication.WebSocketAuthentication;
import org.thehive.hiveserver.websocket.authentication.WebSocketAuthenticationHolder;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@Endpoint(id = "webSocketAuthenticationList")
public class WebSocketAuthenticationListEndpoint {

    private final WebSocketAuthenticationHolder authenticationHolder;

    @ReadOperation
    public Collection<WebSocketAuthentication> list() {
        return authenticationHolder.allUsers();
    }

}
