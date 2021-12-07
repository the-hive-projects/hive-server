package org.thehive.hiveserver.websocket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionInfo implements Payload {

    private long timestamp;
    private String ownerUsername;
    private List<String> participantUsernameList;

}
