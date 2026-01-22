package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.game.Player;

public record AttackDto (
        PlayerDto player,
        int row,
        int column){
}
