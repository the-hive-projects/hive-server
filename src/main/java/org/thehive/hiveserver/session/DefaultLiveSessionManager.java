package org.thehive.hiveserver.session;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.thehive.hiveserver.entity.Session;

public class DefaultLiveSessionManager extends AbstractLiveSessionManager {

    private final SessionJoinIdGenerator joinIdGenerator;

    public DefaultLiveSessionManager(LiveSessionHolderStrategy strategy, @NonNull SessionJoinIdGenerator joinIdGenerator) {
        super(strategy);
        this.joinIdGenerator = joinIdGenerator;
    }

    @Override
    public LiveSession makeSessionLive(Session session) {
        String joinId;
        do {
            joinId = joinIdGenerator.generate();
        } while (containsSession(joinId));
        var liveSession = new LiveSession(joinId,session);
        strategy.add(joinId, liveSession);
        return liveSession;
    }

    @Nullable
    @Override
    public LiveSession terminateSession(String joinId) {
        return strategy.get(joinId);
    }

    @Override
    public boolean containsSession(String joinId) {
        return strategy.contains(joinId);
    }

    @Override
    public LiveSession getSession(String joinId) {
        return strategy.get(joinId);
    }

    @Override
    public int sessionCount() {
        return strategy.count();
    }

}
