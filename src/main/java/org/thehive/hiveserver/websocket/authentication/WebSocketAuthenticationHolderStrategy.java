package org.thehive.hiveserver.websocket.authentication;

import java.util.Collection;

public interface WebSocketAuthenticationHolderStrategy {

    void save(WebSocketAuthentication authentication);

    WebSocketAuthentication get(String username);

    Collection<String> getAllUsernames();

    Collection<WebSocketAuthentication> getAllUsers();

    boolean contains(String username);

    WebSocketAuthentication remove(String username);

    int size();

}
