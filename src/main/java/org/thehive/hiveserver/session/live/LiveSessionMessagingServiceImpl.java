package org.thehive.hiveserver.session.live;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.thehive.hiveserver.websocket.header.AppHeaders;
import org.thehive.hiveserver.websocket.header.PayloadType;
import org.thehive.hiveserver.websocket.payload.*;

@Slf4j(topic = "session")
@RequiredArgsConstructor
public class LiveSessionMessagingServiceImpl implements LiveSessionMessagingService {

    private static final String WEBSOCKET_DESTINATION_PREFIX = "/queue/session";

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendLiveSessionInformation(LiveSession liveSession, String participant) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.LIVE_SESSION_INFORMATION);
        var payload = new LiveSessionInformation();
        payload.setOwner(liveSession.session.getUser().getUsername());
        payload.setDuration(liveSession.session.getDuration());
        payload.setParticipants(liveSession.getCurrentParticipantSet());
        payload.setCreatedAt(liveSession.session.getCreationTime());
        log.info("LiveSessionInformation sending, liveId: {}, participant: {}, payload: {}", liveSession.liveId, participant, payload);
        messagingTemplate.convertAndSendToUser(participant, createSessionDesination(liveSession.liveId), payload, headers);
    }

    @Override
    public void sendParticipationNotification(LiveSession liveSession, String participant, boolean joined) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.PARTICIPATION_NOTIFICATION);
        var payload = new ParticipationNotification();
        payload.setParticipant(participant);
        payload.setJoined(joined);
        payload.setTimestamp(System.currentTimeMillis());
        log.info("ParticipationNotification sending, live-id: {}, payload: {}", liveSession.liveId, payload);
        liveSession.getCurrentParticipantSet()
                .parallelStream()
                .filter(p -> !p.equals(participant))
                .forEach(p -> messagingTemplate.convertAndSendToUser(p, createSessionDesination(liveSession.liveId), payload, headers));
    }

    @Override
    public void sendExpirationNotification(LiveSession liveSession) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.EXPIRATION_NOTIFICATION);
        var payload = new ExpirationNotification();
        payload.setTimestamp(System.currentTimeMillis());
        log.info("ExpirationNotification sending, live-id: {}, payload: {}", liveSession.liveId, payload);
        liveSession.getCurrentParticipantSet()
                .parallelStream()
                .forEach(p -> messagingTemplate.convertAndSendToUser(p, createSessionDesination(liveSession.liveId), payload, headers));
    }

    @Override
    public void sendChatMessage(LiveSession liveSession, ChatMessage payload) {
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.CHAT_MESSAGE);
        log.info(" ChatMessage sending, live-id: {}, payload: {}", liveSession.liveId, payload);
        liveSession.getCurrentParticipantSet()
                .parallelStream()
                .forEach(p -> messagingTemplate.convertAndSendToUser(p, createSessionDesination(liveSession.liveId), payload, headers));
    }

    @Override
    public void sendCodeReceivingRequest(LiveSession liveSession, CodeReceivingRequest payload) {
        if (payload.isStart())
            liveSession.addReceiver(payload.getBroadcaster(), payload.getReceiver());
        else
            liveSession.removeReceiver(payload.getBroadcaster(), payload.getReceiver());
        var headers = new AppHeaders();
        headers.setPayloadType(PayloadType.CODE_RECEIVING_REQUEST);
        log.info(" CodeReceivingRequest sending, live-id: {}, payload: {}", liveSession.liveId, payload);
        messagingTemplate.convertAndSendToUser(payload.getBroadcaster(), createSessionDesination(liveSession.liveId), payload, headers);
    }

    @Override
    public void sendCodeBroadcastInformation(LiveSession liveSession, CodeBroadcastingInformation payload) {
        log.info(" CodeBroadcastingInformation sending, live-id: {}, payload: {}", liveSession.liveId, payload);
    }

    private String createSessionDesination(String liveId) {
        return WEBSOCKET_DESTINATION_PREFIX.concat("/" + liveId);
    }

}
