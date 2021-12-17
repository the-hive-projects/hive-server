package org.thehive.hiveserver.websocket.authentication;

public interface WebSocketAuthenticationHolderStrategy {

    void save(WebSocketAuthentication authentication);

    WebSocketAuthentication get(String username);

    boolean contains(String username);

    WebSocketAuthentication remove(String username);

    int size();

}
