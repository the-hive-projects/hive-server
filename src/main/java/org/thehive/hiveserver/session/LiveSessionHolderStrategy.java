package org.thehive.hiveserver.session;

import java.util.Collection;
import java.util.Set;

public interface LiveSessionHolderStrategy {

    void add(String id,LiveSession session);

    LiveSession get(String id);

    Collection<LiveSession> all();

    LiveSession remove(String id);

    boolean contains(String id);

    int count();

}
