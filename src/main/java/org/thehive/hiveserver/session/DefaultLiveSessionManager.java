package org.thehive.hiveserver.session;

import org.springframework.lang.Nullable;
import org.thehive.hiveserver.entity.Session;

public class DefaultLiveSessionManager extends AbstractLiveSessionManager {

    public DefaultLiveSessionManager(LiveSessionHolderStrategy strategy) {
        super(strategy);
    }

    @Override
    public LiveSession makeSessionLive(Session session) {
        var liveSession = new LiveSession(session);
        strategy.add(liveSession);
        return liveSession;
    }

    @Nullable
    @Override
    public LiveSession terminateSession(String sessionId) {
        return strategy.get(sessionId);
    }

    @Override
    public boolean containsSession(String sessionId) {
        return strategy.contains(sessionId);
    }

    @Override
    public LiveSession getSession(String sessionId) {
        return strategy.get(sessionId);
    }

    @Override
    public int sessionCount() {
        return strategy.count();
    }

}
