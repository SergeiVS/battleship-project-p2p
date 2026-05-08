package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;

import java.io.Serializable;

public record AttackResponseMessage(
        AttackStatus attackStatus,
        CellValue shipType
) implements MessagePayload, Serializable {
    @Override
    public PayloadType type() {
        return PayloadType.ATTACK_RESPONSE;
    }
}
