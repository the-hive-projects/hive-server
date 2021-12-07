package org.thehive.hiveserver.session;

import lombok.NonNull;
import org.thehive.hiveserver.entity.Session;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LiveSession {

    public final Session session;
    private final List<String> allParticipantUsernameList;
    private final List<String> currentParticipantUsernameList;

    LiveSession(@NonNull Session session) {
        this.session = session;
        //TODO better collection implementation
        this.allParticipantUsernameList = Collections.synchronizedList(new LinkedList<>());
        this.currentParticipantUsernameList = Collections.synchronizedList(new LinkedList<>());
    }

    public void addParticipant(String username) {
        allParticipantUsernameList.add(username);
        currentParticipantUsernameList.add(username);
    }

    public void removeParticipant(String username) {
        currentParticipantUsernameList.remove(username);
    }

    public List<String> getAllParticipantUsernameList() {
        return Collections.unmodifiableList(allParticipantUsernameList);
    }

    public List<String> getCurrentParticipantUsernameList() {
        return Collections.unmodifiableList(currentParticipantUsernameList);
    }

    public int allParticipantCount() {
        return allParticipantUsernameList.size();
    }

    public int currentParticipantCount() {
        return currentParticipantUsernameList.size();
    }

}
