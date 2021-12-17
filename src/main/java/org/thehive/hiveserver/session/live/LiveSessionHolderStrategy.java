package org.thehive.hiveserver.session.live;

import java.util.Collection;

public interface LiveSessionHolderStrategy {

    void add(String id, LiveSession session);

    LiveSession get(String id);

    Collection<String> getAllIds();

    Collection<LiveSession> getAllSessions();

    LiveSession remove(String id);

    boolean contains(String id);

    int count();

}
