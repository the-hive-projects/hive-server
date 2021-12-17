package org.thehive.hiveserver.websocket.authentication;

import lombok.NonNull;

import java.security.Principal;

public class WebSocketPrincipal implements Principal {

    private final WebSocketUser webSocketUser;

    public WebSocketPrincipal(@NonNull WebSocketUser webSocketUser) {
        this.webSocketUser = webSocketUser;
    }

    @Override
    public String getName() {
        return webSocketUser.getUsername();
    }

    public WebSocketUser getWebSocketUser() {
        return webSocketUser;
    }

}
