package org.thehive.hiveserver.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RawMessage extends AbstractMessage<String> {

    private MessageType type;
    private String payload;

    public RawMessage(HashMap<String, Object> headers, MessageType type, String payload) {
        super(headers);
        this.type = type;
        this.payload = payload;
    }

}