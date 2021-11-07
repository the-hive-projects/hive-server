package org.thehive.hiveserver.websocket.message;

import java.util.Map;

public interface Header {

    MessageType getType();

    long getTimestamp();

}
