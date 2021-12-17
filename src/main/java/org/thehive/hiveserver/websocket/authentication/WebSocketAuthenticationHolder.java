package org.thehive.hiveserver.websocket.authentication;

public interface WebSocketAuthenticationHolder {

    WebSocketAuthenticationHolderStrategy getStrategy();

    void save (WebSocketAuthentication authentication);

    WebSocketAuthentication get(String username);

    boolean contains(String username);

    void remove(String username);

    int count();

}
