package org.thehive.hiveserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thehive.hiveserver.entity.Session;
import org.thehive.hiveserver.service.SessionService;
import org.thehive.hiveserver.session.SessionProperties;
import org.thehive.hiveserver.session.live.LiveSessionHolder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
@Slf4j(topic = "session")
public class SessionApi {

    private final SessionService sessionService;
    private final LiveSessionHolder liveSessionHolder;
    private final SessionProperties sessionProperties;

    @GetMapping("/{live-id}")
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Session get(@PathVariable("live-id") String liveId) {
        if (!liveSessionHolder.contains(liveId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Live session not found by given joinId, joinId: " + liveId);
        var liveSession = liveSessionHolder.getSession(liveId);
        return liveSession.session;
    }

    @PostMapping
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Session save(@RequestBody Session session) {
        var savedSession = sessionService.save(session);
        var liveSession = liveSessionHolder.addSession(savedSession);
        log.info("LiveSession has been started, liveId: {}, session: {}", liveSession.liveId, liveSession.session);
        return savedSession;
    }

}
