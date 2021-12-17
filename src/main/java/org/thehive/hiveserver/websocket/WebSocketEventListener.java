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
        webSocketAuthenticationHolder.remove(user.getUsername());
        log.info("Disconnection - webSocketUser: {}", user);
        if (user.getLiveId() != null) {
            var liveSession = liveSessionHolder.get(user.getLiveId());
            if (liveSession != null) {
                liveSession.removeParticipant(user.getUsername());
                messagingService.sendParticipationNotification(liveSession, user.getUsername(), false);
            }
        }
    }

    @EventListener
    public void handleSubscription(SessionSubscribeEvent event) {
        var destination = WebSocketUtils.extractDestination(event);
        if (isSessionDestination(destination))
            handleSessionSubscription(event, destination);
    }

    @EventListener
    public void handleUnsubscription(SessionUnsubscribeEvent event) {
        var user = WebSocketUtils.extractWebSocketUser(event);
        if (user.getLiveId() != null) {
            handleSessionUnsubscription(event, user.getLiveId());
        }
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
        if (webSocketUser.getLiveId() != null) {
            var liveSession = liveSessionHolder.get(webSocketUser.getLiveId());
            if (liveSession != null) {
                liveSession.removeParticipant(webSocketUser.getUsername());
                messagingService.sendParticipationNotification(liveSession, webSocketUser.getUsername(), false);
            }
        }
        webSocketUser.setLiveId(liveId);
        var liveSession = liveSessionHolder.get(liveId);
        liveSession.addParticipant(webSocketUser.getUsername());
        messagingService.sendLiveSessionInformation(liveSession, webSocketUser.getUsername());
        messagingService.sendParticipationNotification(liveSession, webSocketUser.getUsername(), true);
    }

    private void handleSessionUnsubscription(SessionUnsubscribeEvent event, String liveId) {
        var webSocketUser = WebSocketUtils.extractWebSocketUser(event);
        webSocketUser.setLiveId(null);
        log.info("Session unsubscription, liveId: {}, securityUser: {}", liveId, webSocketUser);
        var liveSession = liveSessionHolder.remove(liveId);
        liveSession.removeParticipant(webSocketUser.getUsername());
        messagingService.sendParticipationNotification(liveSession, webSocketUser.getUsername(), false);
    }

}
