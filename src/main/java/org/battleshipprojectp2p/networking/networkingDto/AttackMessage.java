package org.battleshipprojectp2p.networking.networkingDto;

import java.io.Serializable;

public record AttackMessage(
        int row,
        int column
) implements MessagePayload, Serializable {
    @Override
    public PayloadType type() {
        return PayloadType.ATTACK;
    }
}
