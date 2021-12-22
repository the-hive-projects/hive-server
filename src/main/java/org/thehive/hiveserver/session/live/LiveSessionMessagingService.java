package org.thehive.hiveserver.session.live;

import org.thehive.hiveserver.websocket.payload.ChatMessage;
import org.thehive.hiveserver.websocket.payload.CodeBroadcastingInformation;
import org.thehive.hiveserver.websocket.payload.CodeReceivingRequest;

public interface LiveSessionMessagingService {

    void sendLiveSessionInformation(LiveSession liveSession, String participant);

    void sendParticipationNotification(LiveSession liveSession, String participant, boolean joined);

    void sendExpirationNotification(LiveSession liveSession);

    void sendChatMessage(LiveSession liveSession, ChatMessage payload);

    void sendCodeBroadcastingNotification(LiveSession liveSession, CodeReceivingRequest request);

    void sendCodeBroadcastInformation(LiveSession liveSession, CodeBroadcastingInformation payload);

}
