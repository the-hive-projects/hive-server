package org.thehive.hiveserver.websocket.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageMarshallerImplTest {

    MessageMarshallerImpl messageMarshaller;

    @BeforeEach
    void init(){
        var objectMapper=new ObjectMapper();
        this.messageMarshaller=new MessageMarshallerImpl(objectMapper);
    }

    @Test
    void marshall() {

    }

    @Test
    void unmarshall() {

    }

    @Test
    void testUnmarshall() {

    }

}