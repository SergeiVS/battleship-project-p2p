package org.battleshipprojectp2p.networking.networkingDto;

import java.io.Serializable;

public record CoinFlipMessage(
        boolean coinFlip
) implements MessagePayload, Serializable {
    @Override
    public PayloadType type() {
        return PayloadType.COIN_FLIP;
    }
}
