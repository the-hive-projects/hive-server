package org.thehive.hiveserver.websocket.authentication;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryWebSocketAuthenticationHolderStrategy implements WebSocketAuthenticationHolderStrategy {

    private final Map<String, WebSocketAuthentication> map;

    public InMemoryWebSocketAuthenticationHolderStrategy() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void save(@NonNull WebSocketAuthentication authentication) {
        map.put(authentication.getUsername(),authentication);
    }

    @Nullable
    @Override
    public WebSocketAuthentication get(String username) {
        return map.get(username);
    }

    @Override
    public boolean contains(@NonNull String username) {
        return map.containsKey(username);
    }

    @Nullable
    @Override
    public WebSocketAuthentication remove(@NonNull String username) {
        return map.remove(username);
    }

    @Override
    public int size() {
        return map.size();
    }

}
