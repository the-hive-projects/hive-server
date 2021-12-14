package org.thehive.hiveserver.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import org.thehive.hiveserver.session.live.LiveSessionHolder;
import org.thehive.hiveserver.session.live.LiveSessionMessagingService;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "session")
public class WebSocketEventListener {

    private final LiveSessionHolder liveSessionHolder;
    private final LiveSessionMessagingService messagingService;

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
        if (isSessionDestination(destination))
            handleSessionSubscription(event, destination);
    }

    @EventListener
    public void handleUnsubscription(SessionUnsubscribeEvent event) {
        var destination = WebSocketUtils.extractDestination(event);
        if (isSessionDestination(destination))
            handleSessionUnsubscription(event, destination);
    }

    private String extractLiveIdFromDestination(String destination) {
        return destination.substring(LiveSessionMessagingService.WEBSOCKET_SESSION_DESTINATION_PREFIX.length() + 1);
    }

    private boolean isSessionDestination(String destination) {
        return destination.startsWith(LiveSessionMessagingService.WEBSOCKET_SESSION_DESTINATION_PREFIX);
    }

    private void handleSessionSubscription(SessionSubscribeEvent event, String destination) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        var liveId = extractLiveIdFromDestination(destination);
        log.info("Session subscription, sessionId: {}, username: {}", liveId, securityUser.getUsername());
        var liveSession = liveSessionHolder.getSession(liveId);
        messagingService.sendParticipationNotification(liveSession, securityUser.getUsername(), true);
        messagingService.sendLiveSessionInformation(liveSession, securityUser.getUsername());
    }

    private void handleSessionUnsubscription(SessionUnsubscribeEvent event, String destination) {
        var securityUser = WebSocketUtils.extractSecurityUser(event);
        var liveId = extractLiveIdFromDestination(destination);
        log.info("Session unsubscription, sessionId: {}, username: {}", liveId, securityUser.getUsername());
        var liveSession = liveSessionHolder.removeSession(liveId);
        messagingService.sendParticipationNotification(liveSession, securityUser.getUsername(), false);
    }

}
