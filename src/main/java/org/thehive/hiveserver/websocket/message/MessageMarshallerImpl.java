package org.thehive.hiveserver.websocket.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.thehive.hiveserver.websocket.message.payload.Information;
import org.thehive.hiveserver.websocket.message.payload.Payload;

@RequiredArgsConstructor
public class MessageMarshallerImpl implements MessageMarshaller {

    private final ObjectMapper objectMapper;

    @Override
    public RawMessage marshall(@NonNull TypeMessage<? extends Payload> message) throws MessageMarshallingException{
        try {
            var rawMessage=new RawMessage();
            rawMessage.setType(message.getType());
            rawMessage.setHeaders(message.getHeaders());
            rawMessage.setPayload(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new MessageMarshallingException(e);
        }
    }

    @Override
    public <T extends Payload> TypeMessage<T> unmarshall(@NonNull RawMessage message) throws MessageMarshallingException{
        return null;
    }

}
