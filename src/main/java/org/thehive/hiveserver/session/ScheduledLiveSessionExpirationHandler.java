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
    @Scheduled
    @Override
    public void checkForExpiration() {
        var sessions=liveSessionManager.allSessions();
    }

    @Override
    public void expireSession(LiveSession liveSession) {

    }

}
