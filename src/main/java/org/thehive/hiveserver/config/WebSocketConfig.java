package org.thehive.hiveserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.thehive.hiveserver.websocket.authentication.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").setHandshakeHandler(new WebSocketPrincipalHandshakeHandler());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue");
        config.setApplicationDestinationPrefixes("/websocket");
    }

    @Bean
    public WebSocketAuthenticationHolderStrategy webSocketAuthenticationHolderStrategy() {
        return new InMemoryWebSocketAuthenticationHolderStrategy();
    }

    @Bean
    public WebSocketAuthenticationHolder webSocketAuthenticationHolder() {
        return new WebSocketAuthenticationHolderImpl(webSocketAuthenticationHolderStrategy());
    }

}
