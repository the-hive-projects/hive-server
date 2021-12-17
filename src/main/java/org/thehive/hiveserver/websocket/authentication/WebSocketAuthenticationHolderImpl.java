package org.thehive.hiveserver.websocket.authentication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WebSocketAuthenticationHolderImpl implements WebSocketAuthenticationHolder {

    private final WebSocketAuthenticationHolderStrategy strategy;

    @Override
    public WebSocketAuthenticationHolderStrategy getStrategy() {
        return strategy;
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
