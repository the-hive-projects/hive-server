package org.thehive.hiveserver.websocket.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thehive.hiveserver.websocket.message.payload.Payload;

@RequiredArgsConstructor
public class MessageMarshallerImpl implements MessageMarshaller {

    private final ObjectMapper objectMapper;

    @Override
    public RawMessage marshall(@NonNull TypeMessage<? extends Payload> message) throws MessageMarshallingException {
        try {
            var rawMessage = new RawMessage();
            rawMessage.setType(message.getType());
            rawMessage.setHeaders(message.getHeaders());
            rawMessage.setPayload(objectMapper.writeValueAsString(message));
            return rawMessage;
        } catch (JsonProcessingException e) {
            throw MessageMarshallingException.wrap(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Payload> TypeMessage<T> unmarshall(@NonNull RawMessage message) throws MessageMarshallingException {
        try {
            var typeMessage = new TypeMessage<T>();
            typeMessage.setType(message.getType());
            typeMessage.setHeaders(message.getHeaders());
            var payload = objectMapper.readValue(message.getPayload(), message.getType().payloadType);
            typeMessage.setPayload((T) payload);
            return typeMessage;
        } catch (JsonProcessingException | ClassCastException e) {
            throw MessageMarshallingException.wrap(e);
        }
    }

    @Override
    public <T extends Payload> TypeMessage<T> unmarshall(RawMessage message, Class<T> type) throws MessageMarshallingException {
        try {
            if (type != message.getType().payloadType)
                throw new IllegalArgumentException("Message's type and parameter type is not compatible");
            var typeMessage = new TypeMessage<T>();
            typeMessage.setType(message.getType());
            typeMessage.setHeaders(message.getHeaders());
            var payload = objectMapper.readValue(message.getPayload(), type);
            typeMessage.setPayload(payload);
            return typeMessage;
        } catch (JsonProcessingException | ClassCastException | IllegalArgumentException e) {
            throw MessageMarshallingException.wrap(e);
        }
    }

}
