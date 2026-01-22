package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.AttackStatus;
import org.battleshipprojectp2p.game.Player;

public record AttackResultDto(
        PlayerDto player,
        AttackStatus attackStatus
) {
}
