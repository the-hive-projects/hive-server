package org.thehive.hiveserver.websocket.authentication;

import java.util.Collection;

public interface WebSocketAuthenticationHolder {

    WebSocketAuthenticationHolderStrategy getStrategy();

    void save(WebSocketAuthentication authentication);

    WebSocketAuthentication get(String username);

    Collection<String> allUsernames();

    Collection<WebSocketAuthentication> allUsers();

    boolean contains(String username);

    void remove(String username);

    int count();

}
