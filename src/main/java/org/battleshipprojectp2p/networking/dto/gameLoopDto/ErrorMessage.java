package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import org.battleshipprojectp2p.error.ErrorType;
import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.EncryptedMessageType;

import java.io.Serializable;

public record ErrorMessage(
        ErrorType errorType,
        String errorMessage
) implements MessagePayload, Serializable {
    @Override
    public EncryptedMessageType type() {
        return EncryptedMessageType.ERROR;
    }
}
