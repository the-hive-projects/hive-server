package org.thehive.hiveserver.websocket.message;

import org.thehive.hiveserver.websocket.message.payload.Payload;

public interface MessageMarshaller {

    RawMessage marshall(TypeMessage<? extends Payload> message) throws MessageMarshallingException;

    <T extends Payload> TypeMessage<T> unmarshall(RawMessage message) throws MessageMarshallingException;

}
