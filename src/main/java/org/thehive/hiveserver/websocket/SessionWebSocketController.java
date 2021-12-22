package org.thehive.hiveserver.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.thehive.hiveserver.session.live.LiveSessionHolder;
import org.thehive.hiveserver.session.live.LiveSessionMessagingService;
import org.thehive.hiveserver.websocket.authentication.WebSocketPrincipal;
import org.thehive.hiveserver.websocket.payload.ChatMessage;
import org.thehive.hiveserver.websocket.payload.CodeBroadcastingInformation;
import org.thehive.hiveserver.websocket.payload.CodeReceivingRequest;

import java.security.Principal;

@Slf4j(topic = "session")
@Controller
@RequiredArgsConstructor
public class SessionWebSocketController {

    private final LiveSessionMessagingService messagingService;
    private final LiveSessionHolder liveSessionHolder;

    @MessageMapping("/session/chat")
    public void chat(@Payload ChatMessage payload, Principal principal) {
        var wsPrincipal = (WebSocketPrincipal) principal;
        var user = wsPrincipal.getWebSocketUser();
        var liveSession = liveSessionHolder.get(user.getLiveId());
        payload.setFrom(principal.getName());
        payload.setTimestamp(System.currentTimeMillis());
        log.info("ChatMessage received - payload: {}, liveId: {}, securiyUser: {}", payload, liveSession.liveId, principal.getName());
        messagingService.sendChatMessage(liveSession, payload);
    }

    @MessageMapping("/session/code/receive")
    public void codeReceive(@Payload CodeReceivingRequest payload, Principal principal) {
        var wsPrincipal = (WebSocketPrincipal) principal;
        var user = wsPrincipal.getWebSocketUser();
        var liveSession = liveSessionHolder.get(user.getLiveId());
        payload.setReceiver(principal.getName());
        messagingService.sendCodeBroadcastingNotification(liveSession, payload);
    }

    @MessageMapping("/session/code/broadcast")
    public void codeBroadcast(@Payload CodeBroadcastingInformation payload, Principal principal) {
        var wsPrincipal = (WebSocketPrincipal) principal;
        var user = wsPrincipal.getWebSocketUser();
        var liveSession = liveSessionHolder.get(user.getLiveId());
        payload.setBroadcaster(principal.getName());
        messagingService.sendCodeBroadcastInformation(liveSession, payload);
    }

}
