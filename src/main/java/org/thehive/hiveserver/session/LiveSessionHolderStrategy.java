package org.thehive.hiveserver.session;

public interface LiveSessionHolderStrategy {

    void add(LiveSession session);

    LiveSession get(String id);

    LiveSession remove(String id);

    boolean contains(String id);

    int count();

}
