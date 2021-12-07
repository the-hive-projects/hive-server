package org.thehive.hiveserver.websocket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class JoinNotification implements Payload {

    private String username;
    private long timestamp;

}
