package org.thehive.hiveserver.websocket.authentication;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.thehive.hiveserver.security.SecurityUtils;

import java.security.Principal;
import java.util.Map;

public class WebSocketPrincipalHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        var securtyUser = SecurityUtils.extractSecurityUser(request);
        return new WebSocketPrincipal(securtyUser.getId(), securtyUser.getUsername());
    }

}
