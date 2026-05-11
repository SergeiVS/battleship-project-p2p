package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;

import com.fasterxml.jackson.annotation.JsonInclude;

public record SignedMessage(
        @JsonInclude
        EncryptedPayload payload,
        int sequence,
        String signature
) implements ServerMessage {
}
