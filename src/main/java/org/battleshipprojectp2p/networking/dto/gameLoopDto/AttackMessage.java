package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.EncryptedMessageType;

import java.io.Serializable;

public record AttackMessage(
        int row,
        int column
) implements MessagePayload, Serializable {
    @Override
    public EncryptedMessageType type() {
        return EncryptedMessageType.ATTACK;
    }
}
