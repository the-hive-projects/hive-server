package org.thehive.hiveserver.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import org.thehive.hiveserver.websocket.header.AppHeaders;
import org.thehive.hiveserver.websocket.header.PayloadType;
import org.thehive.hiveserver.websocket.payload.Information;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "session")
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        log.info("Session Connection - user: {}", securityUser);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        log.info("Session Disconnection - user: {}", securityUser);
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        var destination = WebSocketUtils.extractDestination(event);
        log.info("Session Subscribe - destination: {}, user: {}", destination, securityUser);
        //Send join information to all user in that session
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.INFORMATION);
        var info = new Information();
        info.setTimestamp(System.currentTimeMillis());
        info.setText("User '" + event.getUser().getName() + "' joined");
        info.setTitle("Join");
        messagingTemplate.convertAndSend(destination, info, headers);
    }

    @EventListener
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        var destination = WebSocketUtils.extractDestination(event);
        log.info("Session Subscribe - destination: {}, user: {}", destination, securityUser);
        //Send leave information to all user in that session
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.INFORMATION);
        var info = new Information();
        info.setTimestamp(System.currentTimeMillis());
        info.setText("User '" + event.getUser().getName() + "' left");
        info.setTitle("Left");
        messagingTemplate.convertAndSend(destination, info, headers);
    }

}
