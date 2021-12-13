package org.thehive.hiveserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thehive.hiveserver.entity.Session;
import org.thehive.hiveserver.service.SessionService;
import org.thehive.hiveserver.session.LiveSessionManager;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
public class SessionApi {

    private final SessionService sessionService;
    private final LiveSessionManager liveSessionManager;

    @GetMapping("/{join-id}")
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Session get(@PathVariable("join-id") String joinId) {
        if (!liveSessionManager.containsSession(joinId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Live session not found by given joinId, joinId: " + joinId);
        var liveSession = liveSessionManager.getSession(joinId);
        return liveSession.session;
    }

    @PostMapping
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Session save(@RequestBody Session session) {
        var savedSession = sessionService.save(session);
        liveSessionManager.startSession(savedSession);
        return savedSession;
    }

}
