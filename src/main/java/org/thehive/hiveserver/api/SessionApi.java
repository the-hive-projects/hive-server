package org.thehive.hiveserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thehive.hiveserver.entity.Session;
import org.thehive.hiveserver.security.SecurityUtils;
import org.thehive.hiveserver.service.SessionService;
import org.thehive.hiveserver.session.SessionProperties;
import org.thehive.hiveserver.session.live.LiveSessionHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
@Slf4j(topic = "session")
public class SessionApi {

    private final SessionService sessionService;
    private final LiveSessionHolder liveSessionHolder;
    private final SessionProperties sessionProperties;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public List<Session> get(HttpServletRequest request) {
        var user = SecurityUtils.extractSecurityUser(request);
        return sessionService.findAllByUserId(user.getId());
    }

    @GetMapping("/{live-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Session getLive(@PathVariable("live-id") String liveId) {
        var liveSession = liveSessionHolder.get(liveId);
        if (liveSession == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Live session not found by given joinId, joinId: " + liveId);
        return liveSession.session;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Session save(@RequestBody Session session) {
        validateSessionDuration(session);
        var savedSession = sessionService.save(session);
        var liveSession = liveSessionHolder.add(savedSession);
        savedSession.setLiveId(liveSession.liveId);
        log.info("LiveSession has been started, liveId: {}, session: {}", liveSession.liveId, liveSession.session);
        return savedSession;
    }

    private void validateSessionDuration(Session session) {
        if (session.getDuration() == null || session.getDuration() < sessionProperties.getDuration().getMax().toMillis())
            session.setDuration(sessionProperties.getDuration().getMin().toMillis());
        else
            session.setDuration(sessionProperties.getDuration().getMax().toMillis());
    }

}
