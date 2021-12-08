package org.thehive.hiveserver.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.thehive.hiveserver.websocket.header.AppHeaders;
import org.thehive.hiveserver.websocket.header.PayloadType;
import org.thehive.hiveserver.websocket.payload.ChatMessage;

@Slf4j(topic = "session")
@Controller
@RequiredArgsConstructor
public class SessionWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/session/chat/{id}")
    public void chat(@DestinationVariable("id") String id, @Payload ChatMessage chatMessage, Authentication authentication) {
        chatMessage.setFrom(authentication.getName());
        chatMessage.setTimestamp(System.currentTimeMillis());
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.CHAT_MESSAGE);
        log.info("ChatMessage payload: {}, headers: {}", chatMessage, headers);
        messagingTemplate.convertAndSend("/topic/session/" + id, chatMessage, headers);
    }

}
