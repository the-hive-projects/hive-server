package org.thehive.hiveserver.websocket.header;

public enum PayloadType {

    PARTICIPATION_NOTIFICATION("participation-natification"),
    EXPIRATION_NOTIFICATION("expiration-notificaiton"),
    LIVE_SESSION_INFORMATION("live-session-information"),
    CHAT_MESSAGE("chat-message");

    public final String value;

    PayloadType(String value) {
        this.value = value;
    }

}
