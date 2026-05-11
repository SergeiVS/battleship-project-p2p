package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.EncryptedMessageType;

import java.io.Serializable;

public record ConnectionMessage(
        String user
) implements MessagePayload, Serializable {

    @Override
    public EncryptedMessageType type() {
        return EncryptedMessageType.CONNECT;
    }
}
