package org.thehive.hiveserver.websocket.header;

public enum PayloadType {

    JOIN_NOTIFICATION("join-notification"),
    CHAT_MESSAGE("chat-message"),
    SESSION_INFO("session-info");

    public final String value;

    PayloadType(String value) {
        this.value = value;
    }

}
