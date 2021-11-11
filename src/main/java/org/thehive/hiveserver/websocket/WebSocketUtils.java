package org.thehive.hiveserver.websocket;

import lombok.NonNull;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.util.List;
import java.util.Map;

public class WebSocketUtils {

    public static final String MESSAGE_HEADERS_NATIVE_HEADERS = "nativeHeaders";
    public static final String NATIVE_HEADERS_DESTINATION = "destination";

    public static String extractDestination(@NonNull AbstractSubProtocolEvent event) throws IllegalStateException {
        try {
            var messageHeaders = event.getMessage().getHeaders();
            var nativeHeaders = (Map<?, ?>) messageHeaders.get(MESSAGE_HEADERS_NATIVE_HEADERS);
            var destinationList = (List<?>) nativeHeaders.get(NATIVE_HEADERS_DESTINATION);
            var destination = destinationList.get(0);
            return (String) destination;
        } catch (NullPointerException | ClassCastException e) {
            throw new IllegalStateException(e);
        }
    }

}
