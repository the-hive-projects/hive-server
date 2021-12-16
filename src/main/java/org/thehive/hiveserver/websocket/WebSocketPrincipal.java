package org.thehive.hiveserver.websocket;

import lombok.NonNull;

import java.security.Principal;

public class WebSocketPrincipal implements Principal {

    private final WebSocketUser webSocketUser;

    public WebSocketPrincipal(int id, @NonNull String username) {
        this.webSocketUser = new WebSocketUser(id, username);
    }

    @Override
    public String getName() {
        return webSocketUser.getUsername();
    }

    public WebSocketUser getWebSocketUser() {
        return webSocketUser;
    }

}
