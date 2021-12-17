package org.thehive.hiveserver.websocket.authentication;

public interface WebSocketAuthentication {

    String getUsername();

    WebSocketUser getUser();

}
