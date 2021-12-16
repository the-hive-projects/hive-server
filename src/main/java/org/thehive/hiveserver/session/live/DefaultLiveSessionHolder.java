package org.thehive.hiveserver.session.live;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.thehive.hiveserver.entity.Session;
import org.thehive.hiveserver.session.SessionJoinIdGenerator;

import java.util.Collection;

public class DefaultLiveSessionHolder extends AbstractLiveSessionHolder {

    private final SessionJoinIdGenerator joinIdGenerator;

    public DefaultLiveSessionHolder(LiveSessionHolderStrategy strategy, @NonNull SessionJoinIdGenerator joinIdGenerator) {
        super(strategy);
        this.joinIdGenerator = joinIdGenerator;
    }

    @Override
    public LiveSession add(Session session) {
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
    public LiveSession remove(String liveId) {
        return strategy.remove(liveId);
    }

    @Override
    public LiveSession get(String liveId) {
        return strategy.get(liveId);
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
    public boolean contains(String liveId) {
        return strategy.contains(liveId);
    }

    @Override
    public int count() {
        return strategy.count();
    }

}
