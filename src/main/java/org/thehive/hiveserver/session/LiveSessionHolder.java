package org.thehive.hiveserver.session;

import org.thehive.hiveserver.entity.Session;

import java.util.Collection;

public interface LiveSessionHolder {

    LiveSessionHolderStrategy getSessionHolderStrategy();

    LiveSession addSession(Session session);

    LiveSession removeSession(String liveId);

    LiveSession getSession(String liveId);

    Collection<String> allIds();

    Collection<LiveSession> allSessions();

    boolean contains(String liveId);

    int count();

}
