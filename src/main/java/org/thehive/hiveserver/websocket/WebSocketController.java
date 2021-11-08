package org.thehive.hiveserver.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.thehive.hiveserver.websocket.message.Message;
import org.thehive.hiveserver.websocket.message.MessageMarshaller;
import org.thehive.hiveserver.websocket.message.RawMessage;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageMarshaller messageMarshaller;

    @MessageMapping("/session/{id}")
    public void session(@DestinationVariable("id") String id, @Payload RawMessage message, Authentication authentication) {
        message.addHeader(Message.HEADER_TIMESTAMP, System.currentTimeMillis());
        message.addHeader(Message.HEADER_PRINCIPAL, authentication.getPrincipal());
        System.out.println(message);
        messagingTemplate.convertAndSend("/topic/" + id, message);
    }

}
