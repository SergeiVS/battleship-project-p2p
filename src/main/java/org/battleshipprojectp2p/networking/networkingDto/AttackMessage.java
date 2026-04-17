package org.battleshipprojectp2p.networking.networkingDto;

import java.io.Serializable;

public record AttackMessage(
        int column,
        int row
) implements MessagePayload, Serializable {
    @Override
    public PayloadType type() {
        return PayloadType.ATTACK;
    }
}
