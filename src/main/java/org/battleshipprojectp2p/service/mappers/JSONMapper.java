package org.battleshipprojectp2p.service.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.battleshipprojectp2p.networking.networkingDto.BaseMessage;

public class JSONMapper {

    private final ObjectMapper mapper = new ObjectMapper();


    public String toJson(BaseMessage message) throws JsonProcessingException {
        return mapper.writeValueAsString(message);
    }

    public BaseMessage toBaseMessage(String json) {
        try {
            return mapper.readValue(json, BaseMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
