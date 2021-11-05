package org.thehive.hiveserver.websocket;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Scanner;

class WebSocketEchoTest {

    @Test
    void echoTest() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        var headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Basic dXNlcjpwYXNzd29yZA==");
        stompClient.connect("ws://localhost:8080/stomp", headers, new StompSessionHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                System.out.println("getPayloadType");
                return null;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("handleFrame: " + payload);
            }

            @SneakyThrows
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("afterConnected");
                var s = session.subscribe("/topic/mesa", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        System.out.println("getPayloadType");
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        System.out.println("handleFrame: " + payload);
                    }
                });
                Thread.sleep(5000);
                System.out.println("Sending");
                session.send("/websocket/session/mesa", "asdasd");
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                System.out.println("handleException");
                exception.printStackTrace();
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                System.out.println("handleTransportError");
            }
        });

        new Scanner(System.in).nextLine(); // Don't close immediately.

    }

}
