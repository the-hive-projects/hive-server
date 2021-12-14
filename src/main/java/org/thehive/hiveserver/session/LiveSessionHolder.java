package org.thehive.hiveserver.session;

import org.thehive.hiveserver.entity.Session;

import java.util.Collection;

public interface LiveSessionHolder {

    LiveSessionHolderStrategy getSessionHolderStrategy();

    LiveSession startSession(Session session);

    LiveSession endSession(String joinId);

    LiveSession getSession(String joinId);

    Collection<String> allIds();

    Collection<LiveSession> allSessions();

    boolean contains(String joinId);

    int count();

}
