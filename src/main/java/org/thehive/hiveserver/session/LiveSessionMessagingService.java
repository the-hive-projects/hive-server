package org.thehive.hiveserver.session;

import org.thehive.hiveserver.websocket.payload.ChatMessage;

public interface LiveSessionMessagingService {

    String WEBSOCKET_SESSION_DESTINATION_PREFIX = "/queue/session";

    void sendLiveSessionInformationMessage(LiveSession liveSession, String participant);

    void sendParticipationNotificationMessage(LiveSession liveSession, String participant, boolean joined);

    void onLiveSessionWasExpired(LiveSession liveSession);

    void onUserSentChatMessage(LiveSession liveSession, ChatMessage chatMessage);

}
