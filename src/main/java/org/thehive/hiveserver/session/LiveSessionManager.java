package org.thehive.hiveserver.session;

import org.thehive.hiveserver.entity.Session;

public interface LiveSessionManager {

    LiveSessionHolderStrategy getStrategy();

    LiveSession makeSessionLive(Session session);

    LiveSession getSession(String sessionId);

    LiveSession terminateSession(String sessionId);

    boolean containsSession(String sessionId);

    int sessionCount();

}
