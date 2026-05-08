package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.AttackResponseMessage;

public record AttackResponseDto(
        AttackStatus attackStatus,
        CellValue cellValue
) {
    public AttackResponseDto(AttackResponseMessage message) {
        this(message.attackStatus(), message.shipType());
    }
}
