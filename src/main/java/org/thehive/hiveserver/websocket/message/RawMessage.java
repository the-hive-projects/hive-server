package org.thehive.hiveserver.websocket.message;

import lombok.*;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class RawMessage extends AbstractMessage<String> {

    private MessageType type;
    private String payload;

    public RawMessage(@NonNull MessageType type, @NonNull String payload) {
        this.type = type;
        this.payload = payload;
    }

    public RawMessage(@NonNull MessageType type, @NonNull Map<String, Object> headers, @NonNull String payload) {
        super(headers);
        this.type = type;
        this.payload = payload;
    }

}
