package org.thehive.hiveserver.websocket.message;

import org.thehive.hiveserver.websocket.message.payload.Payload;

public interface Message <T extends Payload> {

    Header header();

    T payload();

}
