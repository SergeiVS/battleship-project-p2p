package org.battleshipprojectp2p.networking.networkingDto;

import java.io.Serializable;

public record ConnectionMessage (
        String user
)implements MessagePayload, Serializable {

    @Override
    public PayloadType type() {
        return PayloadType.CONNECT;
    }
}
