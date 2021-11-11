package org.thehive.hiveserver.websocket.header;

import lombok.NonNull;

import java.util.HashMap;

public class AppHeaders extends HashMap<String, Object> {

    public static final String PAYLOAD_TYPE = "payload-type";

    public void payloadType(@NonNull PayloadType payloadType) {
        put(PAYLOAD_TYPE, payloadType.value);
    }

}
