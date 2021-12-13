package org.thehive.hiveserver;

import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;

class Connect {

    public static final String HTTP_BASIC_AUTHENTICATION_HEADER_NAME = HttpHeaders.AUTHORIZATION;
    static String URL = "ws://localhost:8080/stomp";

    public static String httpBasicAuthenticationToken(@NonNull String username, @NonNull String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    @Test
    void test() throws InterruptedException {
        var wsClient = new StandardWebSocketClient();
        var wsStompClient = new WebSocketStompClient(wsClient);
        final var username = "user";
        final var password = "password";
        var token = httpBasicAuthenticationToken(username, password);
        var headers = new WebSocketHttpHeaders();
        headers.add(HTTP_BASIC_AUTHENTICATION_HEADER_NAME, token);
        var l = new CountDownLatch(1);
        wsStompClient.connect(URL, headers, (StompHeaders) null, new StompSessionHandlerAdapter() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                System.out.println("Connect.getPayloadType");
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                super.handleFrame(headers, payload);
                System.out.println("Connect.handleFrame");
            }

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                super.afterConnected(session, connectedHeaders);
                System.out.println("Connect.afterConnected");
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                super.handleException(session, command, headers, payload, exception);
                System.out.println("Connect.handleException");
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                super.handleTransportError(session, exception);
                System.out.println("Connect.handleTransportError");
            }
        });
        l.await();
    }

}
