package org.thehive.hiveserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thehive.hiveserver.entity.Submission;
import org.thehive.hiveserver.security.SecurityUtils;
import org.thehive.hiveserver.service.SessionService;
import org.thehive.hiveserver.service.SubmissionService;
import org.thehive.hiveserver.session.live.LiveSessionHolder;
import org.thehive.hiveserver.websocket.authentication.WebSocketAuthenticationHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionApi {

    private final SubmissionService submissionService;
    private final SessionService sessionService;
    private final LiveSessionHolder liveSessionHolder;
    private final WebSocketAuthenticationHolder authenticationHolder;

    @GetMapping("/{session-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public List<Submission> getAllSubmissionsBySessionId(@PathVariable("session-id") int sessionId, HttpServletRequest request) {
        var user = SecurityUtils.extractSecurityUser(request);
        if (!sessionService.containsByIdAndUserId(sessionId, user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session owner is not user");
        return submissionService.findAllBySessionId(sessionId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public List<Submission> getAllSubmissionsByUserId(HttpServletRequest request) {
        var user = SecurityUtils.extractSecurityUser(request);
        return submissionService.findAllByUserId(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(security = @SecurityRequirement(name = "generalSecurity"))
    public Submission save(@RequestBody Submission submission, HttpServletRequest request) {
        var user = SecurityUtils.extractSecurityUser(request);
        var authentication = authenticationHolder.get(user.getUsername());
        if (authentication == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a participant of any live session");
        var liveSession = liveSessionHolder.get(authentication.getUser().getLiveId());
        if (liveSession == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a participant of any live session");
        var contains = submissionService.containsByUserIdAndSessionId(user.getId(), liveSession.session.getId());
        if (contains)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has already been submitted before");
        submission.setSession(liveSession.session);
        var savedSubmission = submissionService.save(submission);
        log.info("Submission has been saved, username: {}, submission: {}", user.getUsername(), savedSubmission);
        return savedSubmission;
    }

}