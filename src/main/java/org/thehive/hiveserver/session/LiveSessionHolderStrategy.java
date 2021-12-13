package org.thehive.hiveserver.session;

import java.util.Collection;

public interface LiveSessionHolderStrategy {

    void add(String id, LiveSession session);

    LiveSession get(String id);

    Collection<String> ids();

    Collection<LiveSession> sessions();

    LiveSession remove(String id);

    boolean contains(String id);

    int count();

}
