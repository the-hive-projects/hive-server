package org.thehive.hiveserver.session;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.thehive.hiveserver.entity.Session;

import java.util.Collection;

public class DefaultLiveSessionHolder extends AbstractLiveSessionHolder {

    private final SessionJoinIdGenerator joinIdGenerator;

    public DefaultLiveSessionHolder(LiveSessionHolderStrategy strategy, @NonNull SessionJoinIdGenerator joinIdGenerator) {
        super(strategy);
        this.joinIdGenerator = joinIdGenerator;
    }

    @Override
    public LiveSession startSession(Session session) {
        String joinId;
        do {
            joinId = joinIdGenerator.generate();
        } while (contains(joinId));
        var liveSession = new LiveSession(joinId, session);
        strategy.add(joinId, liveSession);
        return liveSession;
    }

    @Nullable
    @Override
    public LiveSession endSession(String joinId) {
        return strategy.remove(joinId);
    }

    @Override
    public LiveSession getSession(String joinId) {
        return strategy.get(joinId);
    }

    @Override
    public Collection<String> allIds() {
        return strategy.ids();
    }

    @Override
    public Collection<LiveSession> allSessions() {
        return strategy.sessions();
    }

    @Override
    public boolean contains(String joinId) {
        return strategy.contains(joinId);
    }

    @Override
    public int count() {
        return strategy.count();
    }

}
