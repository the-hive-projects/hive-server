package org.thehive.hiveserver.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeaderImpl implements Header {

    private MessageType type;
    private long timestamp;

}
