package org.thehive.hiveserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.thehive.hiveserver.websocket.payload.LiveSessionInformation;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
class WebSocketConnectionTest {

    static String WEBSOCKET_URL = "ws://localhost:8080/stomp";

    @Test
    void connect() throws InterruptedException {
        final var liveId = "33711917054";
        final var authorizationHeaderValue = "Basic dXNlcjpwYXNzd29yZA==";
        var wsClient = new StandardWebSocketClient();
        var wsStompClient = new WebSocketStompClient(wsClient);
        wsStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        var httpHeaders = new WebSocketHttpHeaders();
        httpHeaders.add("Authorization", authorizationHeaderValue);
        var latch = new CountDownLatch(1);
        log.info("LiveId: {}", liveId);
        wsStompClient.connect(WEBSOCKET_URL, httpHeaders, (StompHeaders) null, new StompSessionHandler() {

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                log.info("WebSocketConnectionTest.afterConnected");
                session.subscribe("/user/queue/session/" + liveId, new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        log.info("WebSocketConnectionTest.getPayloadType");
                        var typeStr = headers.getOrDefault("payload-type", List.of("empty")).get(0);
                        Assertions.assertEquals("live-session-information", typeStr);
                        return LiveSessionInformation.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        log.info("WebSocketConnectionTest.handleFrame");
                        log.info("Payload: {}", payload);
                        session.disconnect();
                        latch.countDown();
                    }
                });

            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers,
                                        byte[] payload, Throwable exception) {
                log.info("WebSocketConnectionTest.handleException");
                exception.printStackTrace();
                latch.countDown();
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                log.info("WebSocketConnectionTest.handleTransportError");
                exception.printStackTrace();
                latch.countDown();
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                log.info("WebSocketConnectionTest.getPayloadType");
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info("WebSocketConnectionTest.handleFrame");
            }
        });
        latch.await();
    }

}
