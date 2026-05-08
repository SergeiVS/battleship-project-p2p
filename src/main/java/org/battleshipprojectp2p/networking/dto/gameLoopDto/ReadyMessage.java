package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import java.io.Serializable;

public record ReadyMessage(
        String integrityHash
) implements MessagePayload, Serializable {
    @Override
    public PayloadType type() {
        return PayloadType.READY;
    }
}
