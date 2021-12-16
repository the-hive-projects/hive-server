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

    private static final String WEBSOCKET_SESSION_QUEUE_DESTINATION_PREFIX = "/user/queue/session";

    private final LiveSessionHolder liveSessionHolder;
    private final LiveSessionMessagingService messagingService;

    @EventListener
    public void handleConnection(SessionConnectEvent event) {
        var webSocketUser = WebSocketUtils.extractWebSocketUser(event);
        log.info("Connection - webSocketUser: {}", webSocketUser);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        var webSocketUser = WebSocketUtils.extractWebSocketUser(event);
        log.info("Disconnection - webSocketUser: {}", webSocketUser);
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
        return destination.substring(WEBSOCKET_SESSION_QUEUE_DESTINATION_PREFIX.length() + 1);
    }

    private boolean isSessionDestination(String destination) {
        return destination.startsWith(WEBSOCKET_SESSION_QUEUE_DESTINATION_PREFIX);
    }

    private void handleSessionSubscription(SessionSubscribeEvent event, String destination) {
        var webSocketUser = WebSocketUtils.extractWebSocketUser(event);
        var liveId = extractLiveIdFromDestination(destination);
        log.info("Session subscription, liveId: {}, securityUser: {}", liveId, webSocketUser);
        var liveSession = liveSessionHolder.getSession(liveId);
        liveSession.addParticipant(webSocketUser.getUsername());
        messagingService.sendLiveSessionInformation(liveSession, webSocketUser.getUsername());
        messagingService.sendParticipationNotification(liveSession, webSocketUser.getUsername(), true);
    }

    private void handleSessionUnsubscription(SessionUnsubscribeEvent event, String destination) {
        var webSocketUser = WebSocketUtils.extractWebSocketUser(event);
        var liveId = extractLiveIdFromDestination(destination);
        log.info("Session unsubscription, liveId: {}, securityUser: {}", liveId, webSocketUser);
        var liveSession = liveSessionHolder.removeSession(liveId);
        liveSession.removeParticipant(webSocketUser.getUsername());
        messagingService.sendParticipationNotification(liveSession, webSocketUser.getUsername(), false);
    }

}
