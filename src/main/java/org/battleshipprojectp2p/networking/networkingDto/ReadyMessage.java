package org.battleshipprojectp2p.networking.networkingDto;

import java.io.Serializable;

public record ReadyMessage(
        String integrityHash
)implements MessagePayload, Serializable {
    @Override
    public PayloadType type() {
        return PayloadType.READY;
    }
}
