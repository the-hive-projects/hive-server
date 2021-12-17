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
import org.thehive.hiveserver.websocket.authentication.DefaultWebSocketAuthentication;
import org.thehive.hiveserver.websocket.authentication.WebSocketAuthenticationHolder;
import org.thehive.hiveserver.websocket.authentication.WebSocketUser;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "session")
public class WebSocketEventListener {

    private static final String WEBSOCKET_SESSION_QUEUE_DESTINATION_PREFIX = "/user/queue/session";

    private final LiveSessionHolder liveSessionHolder;
    private final WebSocketAuthenticationHolder webSocketAuthenticationHolder;
    private final LiveSessionMessagingService messagingService;

    @EventListener
    public void handleConnection(SessionConnectEvent event) {
        var user = WebSocketUtils.extractWebSocketUser(event);
        var authentication = new DefaultWebSocketAuthentication(user);
        webSocketAuthenticationHolder.save(authentication);
        log.info("Connection - webSocketUser: {}", user);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        var user = WebSocketUtils.extractWebSocketUser(event);
        handleSessionUnsubscriptionIfParticipant(user);
        webSocketAuthenticationHolder.remove(user.getUsername());
        log.info("Disconnection - webSocketUser: {}", user);
    }

    @EventListener
    public void handleSubscription(SessionSubscribeEvent event) {
        var destination = WebSocketUtils.extractDestination(event);
        if (isSessionDestination(destination))
            handleSessionSubscription(event, destination);
    }

    @EventListener
    public void handleUnsubscription(SessionUnsubscribeEvent event) {
        handleSessionUnsubscription(event);
    }

    private String extractLiveIdFromDestination(String destination) {
        return destination.substring(WEBSOCKET_SESSION_QUEUE_DESTINATION_PREFIX.length() + 1);
    }

    private boolean isSessionDestination(String destination) {
        return destination.startsWith(WEBSOCKET_SESSION_QUEUE_DESTINATION_PREFIX);
    }

    private void handleSessionSubscription(SessionSubscribeEvent event, String destination) {
        var user = WebSocketUtils.extractWebSocketUser(event);
        var liveId = extractLiveIdFromDestination(destination);
        log.info("Session subscription, liveId: {}, webSocketUser: {}", liveId, user);
        handleSessionUnsubscriptionIfParticipant(user);
        user.setLiveId(liveId);
        var liveSession = liveSessionHolder.get(liveId);
        liveSession.addParticipant(user.getUsername());
        messagingService.sendLiveSessionInformation(liveSession, user.getUsername());
        messagingService.sendParticipationNotification(liveSession, user.getUsername(), true);
    }

    private void handleSessionUnsubscription(SessionUnsubscribeEvent event) {
        handleSessionUnsubscriptionIfParticipant(WebSocketUtils.extractWebSocketUser(event));
    }

    private void handleSessionUnsubscriptionIfParticipant(WebSocketUser user) {
        var liveId = user.getLiveId();
        if (liveId != null) {
            user.setLiveId(null);
            log.info("Session unsubscription, liveId: {}, securityUser: {}", liveId, user);
            var liveSession = liveSessionHolder.get(liveId);
            liveSession.removeParticipant(user.getUsername());
            messagingService.sendParticipationNotification(liveSession, user.getUsername(), false);
        }
    }

}
