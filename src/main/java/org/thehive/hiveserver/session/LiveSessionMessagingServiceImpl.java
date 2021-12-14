package org.thehive.hiveserver.session;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.thehive.hiveserver.websocket.header.AppHeaders;
import org.thehive.hiveserver.websocket.header.PayloadType;
import org.thehive.hiveserver.websocket.payload.ChatMessage;
import org.thehive.hiveserver.websocket.payload.ExpirationNotification;
import org.thehive.hiveserver.websocket.payload.LiveSessionInformation;
import org.thehive.hiveserver.websocket.payload.ParticipationNotification;

@RequiredArgsConstructor
public class LiveSessionMessagingServiceImpl implements LiveSessionMessagingService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendLiveSessionInformationMessage(LiveSession liveSession, String participant) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.LIVE_SESSION_INFORMATION);
        var payload = new LiveSessionInformation();
        payload.setOwner(liveSession.session.getCreatedBy().getUsername());
        payload.setDuration(liveSession.session.getDuration());
        payload.setParticipants(liveSession.getCurrentParticipantSet());
        payload.setCreatedAt(liveSession.session.getCreatedAt());
        messagingTemplate.convertAndSendToUser(participant, createSessionDesination(liveSession.liveId), headers);
    }

    @Override
    public void sendParticipationNotificationMessage(LiveSession liveSession, String participant, boolean joined) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.PARTICIPATION_NOTIFICATION);
        var payload = new ParticipationNotification();
        payload.setParticipant(participant);
        payload.setJoined(joined);
        payload.setTimestamp(System.currentTimeMillis());
        liveSession.getCurrentParticipantSet()
                .parallelStream()
                .filter(p -> !p.equals(participant))
                .forEach(p -> messagingTemplate.convertAndSendToUser(p, createSessionDesination(liveSession.liveId), payload, headers));
    }

    @Override
    public void onLiveSessionWasExpired(LiveSession liveSession) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.EXPIRATION_NOTIFICATION);
        var payload = new ExpirationNotification();
        payload.setTimestamp(System.currentTimeMillis());
        liveSession.getCurrentParticipantSet()
                .parallelStream()
                .forEach(p -> messagingTemplate.convertAndSendToUser(p, createSessionDesination(liveSession.liveId), payload, headers));
    }

    @Override
    public void onUserSentChatMessage(LiveSession liveSession, ChatMessage chatMessage) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.CHAT_MESSAGE);
        liveSession.getCurrentParticipantSet()
                .parallelStream()
                .forEach(p -> messagingTemplate.convertAndSendToUser(p, createSessionDesination(liveSession.liveId), chatMessage, headers));
    }

    private String createSessionDesination(String liveId) {
        return LiveSessionMessagingService.WEBSOCKET_SESSION_DESTINATION_PREFIX.concat("/" + liveId);
    }

}
