package org.battleshipprojectp2p.service.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.EncryptedPayload;
import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.ServerMessage;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.BaseMessage;

public class JSONMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public String baseMessageToJson(BaseMessage message) throws JsonProcessingException {
        return mapper.writeValueAsString(message);
    }

    public String encryptedPayloadToJson(EncryptedPayload message) throws JsonProcessingException {
        return mapper.writeValueAsString(message);
    }

    public String signedMessageToJson(ServerMessage message) throws JsonProcessingException {
        return mapper.writeValueAsString(message);
    }


    public BaseMessage toBaseMessage(String json) {
        try {
            return mapper.readValue(json, BaseMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public EncryptedPayload toEncryptedPayload(String json) {
        try {
            return mapper.readValue(json, EncryptedPayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerMessage toServerMessage(String json) {
        try {
            return mapper.readValue(json, ServerMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
