package org.thehive.hiveserver.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.thehive.hiveserver.websocket.message.payload.Payload;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TypeMessage<T extends Payload> extends AbstractMessage<T> {

    private MessageType type;
    private T payload;

    public TypeMessage(Map<String, Object> headers, MessageType type, T payload) {
        super(headers);
        this.type = type;
        this.payload = payload;
    }

}