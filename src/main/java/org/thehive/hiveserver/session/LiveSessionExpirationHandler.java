package org.thehive.hiveserver.session;

public interface LiveSessionExpirationHandler {

    void checkForExpiration();

    void expireSession(LiveSession liveSession);

}
