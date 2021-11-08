package org.thehive.hiveserver.websocket.message;

import lombok.NonNull;
import org.thehive.hiveserver.websocket.message.payload.Chat;
import org.thehive.hiveserver.websocket.message.payload.Information;
import org.thehive.hiveserver.websocket.message.payload.Payload;

public enum MessageType {

    INFO(Information.class),
    CHAT(Chat.class);

    public final Class<? extends Payload> payloadType;

    MessageType(Class<? extends Payload> payloadType) {
        this.payloadType = payloadType;
    }

    public static MessageType fromType(@NonNull Class<? extends Payload> type) throws IllegalArgumentException {
        for (var mt : MessageType.values())
            if (mt.payloadType == type)
                return mt;
        throw new IllegalArgumentException("Given payload type is not supported, type:" + type.getName());
    }

}
