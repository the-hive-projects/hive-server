package org.thehive.hiveserver.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.thehive.hiveserver.session.live.LiveSessionHolder;
import org.thehive.hiveserver.session.live.LiveSessionMessagingService;
import org.thehive.hiveserver.websocket.payload.ChatMessage;

import java.security.Principal;

@Slf4j(topic = "session")
@Controller
@RequiredArgsConstructor
public class SessionWebSocketController {

    private final LiveSessionHolder liveSessionHolder;
    private final LiveSessionMessagingService messagingService;

    @MessageMapping("/session/chat/{live-id}")
    public void chat(@DestinationVariable("live-id") String liveId, @Payload ChatMessage chatMessage, Principal principal) {
        chatMessage.setFrom(principal.getName());
        chatMessage.setTimestamp(System.currentTimeMillis());
        log.info("ChatMessage received - payload: {}, liveId: {}, securiyUser: {}", chatMessage, liveId, principal.getName());
        var liveSession = liveSessionHolder.get(liveId);
        messagingService.sendChatMessage(liveSession, chatMessage);
    }

}
