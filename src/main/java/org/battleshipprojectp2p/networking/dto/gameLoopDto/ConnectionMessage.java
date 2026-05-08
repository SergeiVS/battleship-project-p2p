package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import java.io.Serializable;

public record ConnectionMessage(
        String user
) implements MessagePayload, Serializable {

    @Override
    public PayloadType type() {
        return PayloadType.CONNECT;
    }
}
