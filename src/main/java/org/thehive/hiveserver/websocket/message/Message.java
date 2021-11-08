package org.thehive.hiveserver.websocket.message;

import java.util.Map;

public interface Message<T> {

    String HEADER_TIMESTAMP = "time";
    String HEADER_AUTHENTICATION = "auth";
    String HEADER_PRINCIPAL = "principal";

    MessageType getType();

    Map<String, Object> getHeaders();

    T getPayload();

}
