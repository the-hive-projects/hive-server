package org.thehive.hiveserver.session;

import org.springframework.lang.NonNull;

public abstract class AbstractLiveSessionHolder implements LiveSessionHolder {

    protected final LiveSessionHolderStrategy strategy;

    protected AbstractLiveSessionHolder(@NonNull LiveSessionHolderStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public final LiveSessionHolderStrategy getSessionHolderStrategy() {
        return this.strategy;
    }

}
