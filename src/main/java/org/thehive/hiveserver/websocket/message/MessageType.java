package org.thehive.hiveserver.websocket.message;

import org.thehive.hiveserver.websocket.message.payload.Information;
import org.thehive.hiveserver.websocket.message.payload.Payload;

public enum MessageType {

    INFO(Information.class);

    public final Class<? extends Payload> payloadType;

    MessageType(Class<? extends Payload> payloadType) {
        this.payloadType = payloadType;
    }

}
