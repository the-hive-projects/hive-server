package org.thehive.hiveserver.session.live;

import org.thehive.hiveserver.entity.Session;

import java.util.Collection;

public interface LiveSessionHolder {

    LiveSessionHolderStrategy getSessionHolderStrategy();

    LiveSession add(Session session);

    LiveSession remove(String liveId);

    LiveSession get(String liveId);

    Collection<String> allIds();

    Collection<LiveSession> allSessions();

    boolean contains(String liveId);

    int count();

}
