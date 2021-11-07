package org.thehive.hiveserver.websocket.message;

public class MessageMarshallingException extends RuntimeException{

    public MessageMarshallingException() {
    }

    public MessageMarshallingException(String message) {
        super(message);
    }

    public MessageMarshallingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageMarshallingException(Throwable cause) {
        super(cause);
    }

}
