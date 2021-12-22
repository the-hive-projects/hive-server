package org.thehive.hiveserver.session.live;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.thehive.hiveserver.entity.Session;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

@EqualsAndHashCode
public class LiveSession {

    public final String liveId;
    public final Session session;
    private final Map<String, Set<String>> broadcasterReceiverMap;
    private final Set<String> allParticipantSet;
    private final Set<String> currentParticipantSet;

    public LiveSession(@NonNull String liveId, @NonNull Session session) {
        this.liveId = liveId;
        this.session = session;
        this.broadcasterReceiverMap = new HashMap<>();
        this.allParticipantSet = new ConcurrentSkipListSet<>();
        this.currentParticipantSet = new ConcurrentSkipListSet<>();
    }

    public void addReceiver(String broadcaster, String receiver) {
        broadcasterReceiverMap.computeIfAbsent(broadcaster, k -> new HashSet<>()).add(receiver);
    }

    public void removeReceiver(String broadcaster, String receiver) {
        var receiverSet = broadcasterReceiverMap.get(broadcaster);
        if (receiverSet != null)
            receiverSet.remove(receiver);
    }

    public Set<String> getAllReceivers(String broadcaster) {
        var receiverSet = broadcasterReceiverMap.get(broadcaster);
        if (receiverSet == null)
            return Collections.emptySet();
        return Collections.unmodifiableSet(receiverSet);
    }

    public void addParticipant(String username) {
        allParticipantSet.add(username);
        currentParticipantSet.add(username);
    }

    public void removeParticipant(String username) {
        currentParticipantSet.remove(username);
    }

    public Set<String> getAllParticipants() {
        return Collections.unmodifiableSet(allParticipantSet);
    }

    public Set<String> getCurrentParticipants() {
        return Collections.unmodifiableSet(currentParticipantSet);
    }

    public int allParticipantCount() {
        return allParticipantSet.size();
    }

    public int currentParticipantCount() {
        return currentParticipantSet.size();
    }

}
