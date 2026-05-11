package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.EncryptedMessageType;

import java.io.Serializable;

public record GameOverMessage(
        boolean won,
        String playerBoard
) implements MessagePayload, Serializable {
    @Override
    public EncryptedMessageType type() {
        return EncryptedMessageType.GAME_OVER;
    }
}
