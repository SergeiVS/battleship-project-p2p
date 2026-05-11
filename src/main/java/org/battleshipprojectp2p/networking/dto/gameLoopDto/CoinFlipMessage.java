package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.EncryptedMessageType;

import java.io.Serializable;

public record CoinFlipMessage(
        boolean coinFlip
) implements MessagePayload, Serializable {
    @Override
    public EncryptedMessageType type() {
        return EncryptedMessageType.COIN_FLIP;
    }
}
