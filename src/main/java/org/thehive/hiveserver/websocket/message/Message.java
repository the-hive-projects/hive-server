package org.thehive.hiveserver.websocket.message;

import java.util.Map;

public interface Message<T> {

    String HEADER_TIMESTAMP_KEY = "timestamp";
    String HEADER_AUTHENTICATION_KEY = "timestamp";

    MessageType getType();

    Map<String, Object> getHeaders();

    T getPayload();

}
