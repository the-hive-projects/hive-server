package org.thehive.hiveserver.session.live;

public interface LiveSessionExpirationHandler {

    void checkForExpiration();

    void expireSession(LiveSession liveSession);

}
