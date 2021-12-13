package org.thehive.hiveserver.websocket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionInformation implements Payload {

    private String ownerUsername;
    private Set<String> participantSet;
    private long timestamp;

}
