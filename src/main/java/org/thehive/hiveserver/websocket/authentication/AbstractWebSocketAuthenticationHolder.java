package org.thehive.hiveserver.websocket.authentication;

import lombok.NonNull;

public abstract class AbstractWebSocketAuthenticationHolder implements WebSocketAuthenticationHolder {

    protected final WebSocketAuthenticationHolderStrategy strategy;

    protected AbstractWebSocketAuthenticationHolder(@NonNull WebSocketAuthenticationHolderStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public final WebSocketAuthenticationHolderStrategy getStrategy() {
        return strategy;
    }

}
