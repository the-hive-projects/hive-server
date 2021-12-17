package org.thehive.hiveserver.websocket.authentication;

import lombok.NonNull;

import java.util.Collection;

public class WebSocketAuthenticationHolderImpl extends AbstractWebSocketAuthenticationHolder {

    public WebSocketAuthenticationHolderImpl(@NonNull WebSocketAuthenticationHolderStrategy strategy) {
        super(strategy);
    }

    @Override
    public void save(WebSocketAuthentication authentication) {
        strategy.save(authentication);
    }

    @Override
    public WebSocketAuthentication get(String username) {
        return strategy.get(username);
    }

    @Override
    public Collection<String> allUsernames() {
        return strategy.getAllUsernames();
    }

    @Override
    public Collection<WebSocketAuthentication> allUsers() {
        return strategy.getAllUsers();
    }

    @Override
    public boolean contains(String username) {
        return strategy.contains(username);
    }

    @Override
    public void remove(String username) {
        strategy.remove(username);
    }

    @Override
    public int count() {
        return strategy.size();
    }

}
