package org.thehive.hiveserver.websocket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeWatchRequest implements Payload {

    private long timestamp;
    private boolean isStartWatching;
    private String watcherUsername;
    private String broadcasterUsername;

}
