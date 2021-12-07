package org.thehive.hiveserver.session;

import org.thehive.hiveserver.entity.Session;

public interface LiveSessionManager {

    LiveSession startSession(Session session);

    LiveSession terminateSession(String sessionId);

    boolean containsSession(String sessionId);

    LiveSession getSession(String sessionId);

    int sessionCount();

}
