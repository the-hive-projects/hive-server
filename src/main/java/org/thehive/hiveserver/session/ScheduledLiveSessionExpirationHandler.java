package org.thehive.hiveserver.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j(topic = "session")
@RequiredArgsConstructor
public class ScheduledLiveSessionExpirationHandler implements LiveSessionExpirationHandler {

    private final LiveSessionHolder liveSessionHolder;

    @Async
    @Scheduled(fixedDelayString = "${session.duration.expiration.checkTimeInterval}")
    @Override
    public void checkForExpiration() {
        var currentTimeMs = System.currentTimeMillis();
        var liveSessions = liveSessionHolder.allSessions();
        liveSessions.parallelStream()
                .filter(liveSession -> liveSession.session.getDuration() <= currentTimeMs - liveSession.session.getCreatedAt())
                .forEach(this::expireSession);
    }

    @Override
    public void expireSession(LiveSession liveSession) {
        liveSessionHolder.endSession(liveSession.joinId);
        log.info("Live Session has been ended, joinId: {}, session: {}", liveSession.joinId, liveSession.session);
    }

}
