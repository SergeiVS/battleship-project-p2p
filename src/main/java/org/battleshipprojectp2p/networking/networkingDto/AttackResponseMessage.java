package org.battleshipprojectp2p.networking.networkingDto;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.ship.ShipType;

import java.io.Serializable;

public record AttackResponseMessage(
        AttackStatus attackStatus,
        ShipType shipType
) implements MessagePayload, Serializable {
    @Override
    public PayloadType type() {
        return PayloadType.ATTACK_RESPONSE;
    }
}
