package org.thehive.hiveserver.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class ScheduledLiveSessionExpirationHandler implements LiveSessionExpirationHandler {

    private final LiveSessionManager liveSessionManager;

    @Async
    @Scheduled(fixedDelayString = "${session.duration.expiration.checkTimeInterval}")
    @Override
    public void checkForExpiration() {
        var currentTimeMs = System.currentTimeMillis();
        var liveSessions = liveSessionManager.allSessions();
        liveSessions.parallelStream()
                .filter(liveSession -> liveSession.session.getDuration() <= currentTimeMs - liveSession.session.getCreatedAt())
                .forEach(this::expireSession);
    }

    @Override
    public void expireSession(LiveSession liveSession) {
        log.info("LiveSession has been expired, joinId: {}", liveSession.joinId);
        liveSessionManager.endSession(liveSession.joinId);
    }

}
