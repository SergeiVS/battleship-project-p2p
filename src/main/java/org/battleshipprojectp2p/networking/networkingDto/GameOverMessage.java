package org.battleshipprojectp2p.networking.networkingDto;

import java.io.Serializable;

public record GameOverMessage(
        boolean won,
        String playerBoard
) implements MessagePayload, Serializable {
    @Override
    public PayloadType type() {
        return PayloadType.GAME_OVER;
    }
}
