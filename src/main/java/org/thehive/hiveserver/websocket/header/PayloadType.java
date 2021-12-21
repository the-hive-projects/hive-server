package org.thehive.hiveserver.websocket.header;

public enum PayloadType {

    LIVE_SESSION_INFORMATION("live-session-information"),
    PARTICIPATION_NOTIFICATION("participation-notification"),
    EXPIRATION_NOTIFICATION("expiration-notification"),
    CHAT_MESSAGE("chat-message"),
    CODE_RECEIVING_REQUEST("code-receiving-request"),
    CODE_BROADCASTING_INFORAMTION("code-broadcasting-information");

    public final String value;

    PayloadType(String value) {
        this.value = value;
    }

}
