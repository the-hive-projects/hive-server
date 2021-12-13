package org.thehive.hiveserver.session;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLiveSessionHolderStrategy implements LiveSessionHolderStrategy {

    private final Map<String, LiveSession> idLiveSessionMap;

    public InMemoryLiveSessionHolderStrategy() {
        this.idLiveSessionMap = new ConcurrentHashMap<>();
    }

    @Override
    public void add(@NonNull String id, @NonNull LiveSession session) {
        idLiveSessionMap.put(id, session);
    }

    @Nullable
    @Override
    public LiveSession get(@NonNull String id) {
        return idLiveSessionMap.get(id);
    }

    @Nullable
    @Override
    public LiveSession remove(@NonNull String id) {
        return idLiveSessionMap.remove(id);
    }

    @Override
    public boolean contains(@NonNull String id) {
        return idLiveSessionMap.containsKey(id);
    }

    @Override
    public int count() {
        return idLiveSessionMap.size();
    }

}
