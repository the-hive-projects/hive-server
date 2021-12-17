package org.thehive.hiveserver.websocket.authentication;

import lombok.ToString;

@ToString
public class WebSocketUser {

    private final int id;
    private final String username;
    private String liveId;

    public WebSocketUser(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

}
