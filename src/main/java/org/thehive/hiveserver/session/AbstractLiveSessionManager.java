package org.thehive.hiveserver.session;

import org.springframework.lang.NonNull;

public abstract class AbstractLiveSessionManager implements LiveSessionManager {

    protected final LiveSessionHolderStrategy strategy;

    protected AbstractLiveSessionManager(@NonNull LiveSessionHolderStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public final LiveSessionHolderStrategy getStrategy() {
        return this.strategy;
    }

}
