package org.thehive.hiveserver.session;

import org.thehive.hiveserver.entity.Session;

public interface LiveSessionManager {

    LiveSessionHolderStrategy getStrategy();

    LiveSession makeSessionLive(Session session);

    LiveSession getSession(String joinId);

    LiveSession terminateSession(String joinId);

    boolean containsSession(String joinId);

    int sessionCount();

}
