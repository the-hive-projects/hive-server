package org.thehive.hiveserver.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.thehive.hiveserver.websocket.message.*;
import org.thehive.hiveserver.websocket.message.payload.Chat;

import java.lang.reflect.Type;
import java.util.Scanner;

class WebSocketEchoTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MessageMarshaller messageMarshaller = new MessageMarshallerImpl(objectMapper);

    @Test
    void echoTest() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        var headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Basic dXNlcjpwYXNzd29yZA==");
        stompClient.connect("ws://localhost:8080/stomp", headers, new StompSessionHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                System.out.println("getPayloadType");
                return RawMessage.class;
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
                        return RawMessage.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        System.out.println("handleFrame: " + payload);
                    }
                });
                Thread.sleep(5000);
                System.out.println("Sending");
                var chat = new Chat(System.currentTimeMillis(), "Hello");
                var message = new TypeMessage<>(MessageType.CHAT, chat);
                var rawMessage = messageMarshaller.marshall(message);
                session.send("/websocket/session/mesa", rawMessage);
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

        new Scanner(System.in).nextLine();

    }

}
