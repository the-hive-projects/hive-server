package org.thehive.hiveserver.session;

import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.thehive.hiveserver.entity.Session;

import java.util.concurrent.ConcurrentHashMap;

public class LiveSessionManagerImpl implements LiveSessionManager {

    private final ConcurrentHashMap<String, LiveSession> sessionIdLiveSessionMap;

    public LiveSessionManagerImpl() {
        this.sessionIdLiveSessionMap = new ConcurrentHashMap<>();
    }

    @Override
    public LiveSession startSession(@NonNull Session session) {
        var liveSession = new LiveSession(session);
        sessionIdLiveSessionMap.put(session.getId(), liveSession);
        return liveSession;
    }

    @Nullable
    @Override
    public LiveSession terminateSession(@NonNull String sessionId) {
        if (containsSession(sessionId))
            return sessionIdLiveSessionMap.remove(sessionId);
        return null;
    }

    @Override
    public boolean containsSession(@NonNull String sessionId) {
        return sessionIdLiveSessionMap.containsKey(sessionId);
    }

    @Override
    public LiveSession getSession(@NonNull String sessionId) {
        return sessionIdLiveSessionMap.get(sessionId);
    }

    @Override
    public int sessionCount() {
        return sessionIdLiveSessionMap.size();
    }

}
