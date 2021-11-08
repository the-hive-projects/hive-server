package org.thehive.hiveserver.websocket.message;

import lombok.*;
import org.thehive.hiveserver.websocket.message.payload.Payload;

import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class TypeMessage<T extends Payload> extends AbstractMessage<T> {

    private MessageType type;
    private T payload;

    public TypeMessage(@NonNull MessageType type, @NonNull T payload) {
        this.type = type;
        this.payload = payload;
    }

    public TypeMessage(@NonNull MessageType type, @NonNull Map<String, Object> headers, @NonNull T payload) {
        super(headers);
        this.type = type;
        this.payload = payload;
    }

}
