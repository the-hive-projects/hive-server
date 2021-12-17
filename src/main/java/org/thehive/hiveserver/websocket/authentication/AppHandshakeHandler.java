package org.thehive.hiveserver.websocket.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.thehive.hiveserver.security.SecurityUtils;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
public class AppHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        var securtyUser = SecurityUtils.extractSecurityUser(request);
        var user = new WebSocketUser(securtyUser.getId(), securtyUser.getUsername());
        return new WebSocketPrincipal(user);
    }

}
