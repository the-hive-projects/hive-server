package org.thehive.hiveserver.session.live;

import org.thehive.hiveserver.websocket.payload.ChatMessage;

public interface LiveSessionMessagingService {

    String WEBSOCKET_SESSION_DESTINATION_PREFIX = "/queue/session";

    void sendLiveSessionInformation(LiveSession liveSession, String participant);

    void sendParticipationNotification(LiveSession liveSession, String participant, boolean joined);

    void sendExpirationNotification(LiveSession liveSession);

    void sendChatMessage(LiveSession liveSession, ChatMessage chatMessage);

}
