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
import org.thehive.hiveserver.websocket.payload.Chat;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{id}")
    public void chat(@DestinationVariable("id") String id, @Payload Chat chat, Authentication authentication) {
        chat.setFrom(authentication.getName());
        var headers = new AppHeaders();
        headers.payloadType(PayloadType.CHAT);
        log.info("Chat - payload: {}, headers: {}", chat, headers);
        messagingTemplate.convertAndSend("/topic/" + id, chat, headers);
    }

}
