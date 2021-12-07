package org.thehive.hiveserver.websocket.header;

public enum PayloadType {

    JOIN_NOTIFICATION("join-notification"),
    LEAVE_NOTIFICATION("leave-notification"),
    CHAT_MESSAGE("chat-message"),
    SESSION_INFORMATION("session-information"),
    TERMINATION_NOTIFICATION("termination-notification");

    public final String value;

    PayloadType(String value) {
        this.value = value;
    }

}
