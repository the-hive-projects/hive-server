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
import org.thehive.hiveserver.websocket.payload.LeaveNotification;
import org.thehive.hiveserver.websocket.payload.SessionInformation;
import org.thehive.hiveserver.websocket.payload.TerminationNotification;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "session")
public class WebSocketEventListener {

    private static final String SESSION_TOPIC_DESTINATION = "/topic/session";

    private final SimpMessagingTemplate messagingTemplate;
    private final LiveSessionManager sessionManager;

    @EventListener
    public void handleConnection(SessionConnectEvent event) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        log.info("Connection - user: {}", securityUser);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        log.info("Disconnection - user: {}", securityUser);
    }

    @EventListener
    public void handleSubscription(SessionSubscribeEvent event) {
        var destination = WebSocketUtils.extractDestination(event);
        if (isSessionTopicDestination(destination))
            handleSessionSubscription(event, destination);
    }

    @EventListener
    public void handleUnsubscription(SessionUnsubscribeEvent event) {
        var destination = WebSocketUtils.extractDestination(event);
        if (isSessionTopicDestination(destination))
            handleSessionUnsubscription(event, destination);
    }

    private String extractSessionIdFromDestination(String destination) {
        return destination.substring(SESSION_TOPIC_DESTINATION.length() + 1);
    }

    private boolean isSessionTopicDestination(String destination) {
        return destination.startsWith(SESSION_TOPIC_DESTINATION);
    }

    private void handleSessionSubscription(SessionSubscribeEvent event, String destination) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        var sessionId = extractSessionIdFromDestination(destination);
        log.info("Session subscription, sessionId: {}, username: {}", sessionId, securityUser.getUsername());
        sendJoinNotificationMessage(destination, securityUser.getUsername());
        sendSessionInfoMessage(destination);
    }

    private void handleSessionUnsubscription(SessionUnsubscribeEvent event, String destination) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        var sessionId = extractSessionIdFromDestination(destination);
        log.info("Session unsubscription, sessionId: {}, username: {}", sessionId, securityUser.getUsername());
        sendLeaveNotificationMessage(destination, securityUser.getUsername());
        var liveSession = sessionManager.getSession(sessionId);
        if (liveSession.session.getCreatedBy().getUsername().equals(securityUser.getUsername())) {
            sendTerminationNotificationMessage(destination);
        }
    }

    private void sendJoinNotificationMessage(String destination, String username) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.JOIN_NOTIFICATION);
        var payload = new JoinNotification();
        payload.setUsername(username);
        payload.setTimestamp(System.currentTimeMillis());
        messagingTemplate.convertAndSend(destination, payload, headers);
    }

    private void sendLeaveNotificationMessage(String destination, String username) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.LEAVE_NOTIFICATION);
        var payload = new LeaveNotification();
        payload.setUsername(username);
        payload.setTimestamp(System.currentTimeMillis());
        messagingTemplate.convertAndSend(destination, payload, headers);
    }

    private void sendSessionInfoMessage(String destination) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.SESSION_INFORMATION);
        var liveSession = sessionManager.getSession(extractSessionIdFromDestination(destination));
        var payload = new SessionInformation();
        payload.setOwnerUsername(liveSession.session.getCreatedBy().getUsername());
        payload.setParticipantSet(liveSession.getCurrentParticipantSet());
        payload.setTimestamp(System.currentTimeMillis());
        messagingTemplate.convertAndSend(destination, payload, headers);
    }

    private void sendTerminationNotificationMessage(String destination) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.TERMINATION_NOTIFICATION);
        var payload = new TerminationNotification();
        payload.setTimestamp(System.currentTimeMillis());
        messagingTemplate.convertAndSend(destination, payload, headers);
    }

}
