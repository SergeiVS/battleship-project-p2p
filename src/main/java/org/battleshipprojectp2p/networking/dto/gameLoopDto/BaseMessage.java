package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;

public record BaseMessage(
        PayloadType type,
        @JsonInclude
        MessagePayload payload
) implements Serializable {
    public BaseMessage(PayloadType type, MessagePayload payload) {
        if (!type.equals(payload.type())) {
            throw new IllegalArgumentException("Massage payload type mismatch");
        }
        this.type = Objects.requireNonNull(type);
        this.payload = Objects.requireNonNull(payload);
    }

}
