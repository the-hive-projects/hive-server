package org.thehive.hiveserver.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thehive.hiveserver.entity.Submission;
import org.thehive.hiveserver.error.LiveSessionExpirationException;
import org.thehive.hiveserver.service.SubmissionService;
import org.thehive.hiveserver.session.live.LiveSessionHolder;

import java.util.List;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionApi {

    private final SubmissionService submissionService;
    private final LiveSessionHolder liveSessionHolder;

    @GetMapping("/session/{session-id}")
    public List<Submission> getAllSubmissionsBySessionId(@PathVariable("session-id") int sessionId) {
        return submissionService.findAllBySessionId(sessionId);
    }

    @GetMapping("/user/{user-id}")
    public List<Submission> getAllSubmissionsByUserId(@PathVariable("user-id") int userId) {
        return submissionService.findAllByUserId(userId);
    }

    @PostMapping("/{live-id}")
    public Submission save(@RequestBody Submission submission,
                           @PathVariable("live-id") String liveId) {
        var liveSession = liveSessionHolder.get(liveId);
        if (liveSession == null)
            throw new LiveSessionExpirationException();
        submission.setSession(liveSession.session);
        return submissionService.save(submission);
    }

}