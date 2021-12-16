package org.thehive.hiveserver.session.live;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.thehive.hiveserver.entity.Session;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@EqualsAndHashCode
public class LiveSession {

    public final String liveId;
    public final Session session;
    private final Set<String> allParticipantSet;
    private final Set<String> currentParticipantSet;

    public LiveSession(@NonNull String liveId, @NonNull Session session) {
        this.liveId = liveId;
        this.session = session;
        this.allParticipantSet = new ConcurrentSkipListSet<>();
        this.currentParticipantSet = new ConcurrentSkipListSet<>();
    }

    public void addParticipant(String username) {
        allParticipantSet.add(username);
        currentParticipantSet.add(username);
    }

    public void removeParticipant(String username) {
        currentParticipantSet.remove(username);
    }

    public Set<String> getAllParticipantSet() {
        return Collections.unmodifiableSet(allParticipantSet);
    }

    public Set<String> getCurrentParticipantSet() {
        return Collections.unmodifiableSet(currentParticipantSet);
    }

    public int allParticipantCount() {
        return allParticipantSet.size();
    }

    public int currentParticipantCount() {
        return currentParticipantSet.size();
    }

}