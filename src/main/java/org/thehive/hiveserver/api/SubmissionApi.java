package org.thehive.hiveserver.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thehive.hiveserver.entity.Submission;
import org.thehive.hiveserver.security.SecurityUtils;
import org.thehive.hiveserver.service.SubmissionService;
import org.thehive.hiveserver.session.live.LiveSessionHolder;
import org.thehive.hiveserver.websocket.authentication.WebSocketAuthenticationHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionApi {

    private final SubmissionService submissionService;
    private final LiveSessionHolder liveSessionHolder;
    private final WebSocketAuthenticationHolder authenticationHolder;

    @GetMapping("/session/{session-id}")
    public List<Submission> getAllSubmissionsBySessionId(@PathVariable("session-id") int sessionId) {
        return submissionService.findAllBySessionId(sessionId);
    }

    @GetMapping("/user/{user-id}")
    public List<Submission> getAllSubmissionsByUserId(@PathVariable("user-id") int userId) {
        return submissionService.findAllByUserId(userId);
    }

    @PostMapping
    public Submission save(@RequestBody Submission submission,
                           HttpServletRequest request) {
        var securityUser = SecurityUtils.extractSecurityUser(request);
        var authentication = authenticationHolder.get(securityUser.getUsername());
        if (authentication == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a participant of any live session");
        var liveSession = liveSessionHolder.get(authentication.getUser().getLiveId());
        if (liveSession == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a participant of any live session");
        submission.setSession(liveSession.session);
        return submissionService.save(submission);
    }

}