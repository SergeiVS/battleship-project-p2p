package org.battleshipprojectp2p.service.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.DiffieHellmanMessage;
import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.EncryptedPayload;
import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.SignedMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONMapperTest {

    JSONMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new JSONMapper();
    }

    @AfterEach
    void tearDown() {
        mapper = null;
    }

    @Test
    void shouldSerializeAndDeserializeEncryptedPayloadMessage() throws JsonProcessingException {
        EncryptedPayload payload = new EncryptedPayload("payload", "iv");
        String json = "{\"payload\":\"payload\",\"iv\":\"iv\"}";

        var resultJson = mapper.encryptedPayloadToJson(payload);
        assertEquals(json, resultJson);

        var resultPayload = mapper.toEncryptedPayload(resultJson);
        assertEquals(payload, resultPayload);
    }

    @Test
    void shouldSerializeAndDeserializeSignedMessage() throws JsonProcessingException {
        EncryptedPayload payload = new EncryptedPayload("payload", "iv");
        SignedMessage messageSM = new SignedMessage(payload, 1, "signature");
        String jsonSM = "{\"payload\":{\"payload\":\"payload\",\"iv\":\"iv\"},\"sequence\":1,\"signature\":\"signature\"}";

        DiffieHellmanMessage messageDH = new DiffieHellmanMessage("publicValue");
        String jsonDH = "{\"publicValue\":\"publicValue\"}";

        var resultJsonSM = mapper.signedMessageToJson(messageSM);
        var resultJsonDH = mapper.signedMessageToJson(messageDH);
        assertEquals(jsonSM, resultJsonSM);
        assertEquals(jsonDH, resultJsonDH);


        var resultMessageSM = mapper.toServerMessage(jsonSM);
        var resultMessageDH = mapper.toServerMessage(jsonDH);
        assertEquals(messageSM, resultMessageSM);
        assertEquals(messageDH, resultMessageDH);
    }
}