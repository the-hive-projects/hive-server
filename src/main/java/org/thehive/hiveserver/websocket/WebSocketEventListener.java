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
import org.thehive.hiveserver.session.LiveSessionManager;
import org.thehive.hiveserver.websocket.header.AppHeaders;
import org.thehive.hiveserver.websocket.header.PayloadType;
import org.thehive.hiveserver.websocket.payload.JoinNotification;
import org.thehive.hiveserver.websocket.payload.SessionInfo;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "session")
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final LiveSessionManager sessionManager;

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
        if (destination.startsWith("/topic/session")) {
            //send info message
            var infoMessageHeaders = new AppHeaders();
            infoMessageHeaders.setPayloadType(PayloadType.JOIN_NOTIFICATION);
            var info = new JoinNotification();
            info.setTimestamp(System.currentTimeMillis());
            info.setTitle("Join");
            info.setText("User '" + securityUser.getUsername() + "' joined");
            messagingTemplate.convertAndSend(destination, info, infoMessageHeaders);
            //send sessionInfo message
            var sessionInfoMessageHeaders = new AppHeaders();
            sessionInfoMessageHeaders.setPayloadType(PayloadType.SESSION_INFO);
            var liveSession = sessionManager.getSession(destination);
            var sessionInfo = new SessionInfo();
            sessionInfo.setTimestamp(System.currentTimeMillis());
            sessionInfo.setOwnerUsername(liveSession.session.getCreatedBy().getUsername());
            sessionInfo.setParticipantUsernameList(liveSession.getCurrentParticipantUsernameList());
            messagingTemplate.convertAndSendToUser(securityUser.getUsername(), destination, sessionInfo, sessionInfoMessageHeaders);
        }
    }

    @EventListener
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        var destination = WebSocketUtils.extractDestination(event);
        log.info("Session Subscribe - destination: {}, user: {}", destination, securityUser);
        if (destination.startsWith("/chat")) {
            var headers = new AppHeaders();
            headers.setPayloadType(PayloadType.JOIN_NOTIFICATION);
            var info = new JoinNotification();
            info.setTitle("Left");
            info.setText("User '" + securityUser.getUsername() + "' left");
            info.setTimestamp(System.currentTimeMillis());
            messagingTemplate.convertAndSend(destination, info, headers);
        }
    }

}
