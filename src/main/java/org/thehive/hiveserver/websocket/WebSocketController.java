package org.thehive.hiveserver.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.thehive.hiveserver.websocket.message.RawMessage;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/session/{id}")
    public void echo(@DestinationVariable("id") String id, @Payload RawMessage message, Authentication authentication) {
        var msg = authentication.getName() + " : " + message;
        messagingTemplate.convertAndSend("/topic/" + id, msg);
    }

}
