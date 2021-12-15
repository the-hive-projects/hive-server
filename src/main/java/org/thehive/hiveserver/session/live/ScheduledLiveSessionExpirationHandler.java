package org.thehive.hiveserver.session.live;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j(topic = "session")
@RequiredArgsConstructor
public class ScheduledLiveSessionExpirationHandler implements LiveSessionExpirationHandler {

    private final LiveSessionHolder liveSessionHolder;
    private final LiveSessionMessagingService liveSessionMessagingService;

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
        log.info("Live Session is being ending, joinId: {}, session: {}", liveSession.liveId, liveSession.session);
        liveSessionHolder.removeSession(liveSession.liveId);
        liveSessionMessagingService.sendExpirationNotification(liveSession);
    }

}
