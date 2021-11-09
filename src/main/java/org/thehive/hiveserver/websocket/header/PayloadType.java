package org.thehive.hiveserver.websocket.header;

public enum PayloadType {

    INFORMATION("information"),
    CHAT("chat");

    public final String value;

    PayloadType(String value) {
        this.value = value;
    }

}
