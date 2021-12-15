package org.thehive.hiveserver.session.live;

import org.thehive.hiveserver.websocket.payload.ChatMessage;

public interface LiveSessionMessagingService {

    void sendLiveSessionInformation(LiveSession liveSession, String participant);

    void sendParticipationNotification(LiveSession liveSession, String participant, boolean joined);

    void sendExpirationNotification(LiveSession liveSession);

    void sendChatMessage(LiveSession liveSession, ChatMessage payload);

}
