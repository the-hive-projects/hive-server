package org.thehive.hiveserver.websocket.authentication;

import lombok.NonNull;

public class DefaultWebSocketAuthentication implements WebSocketAuthentication {

    private final WebSocketUser user;

    public DefaultWebSocketAuthentication(@NonNull WebSocketUser user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public WebSocketUser getUser() {
        return user;
    }

}
