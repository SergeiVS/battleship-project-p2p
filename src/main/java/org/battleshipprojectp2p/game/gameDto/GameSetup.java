package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.game.player.Player;

public record GameSetup(
        Player player,
        Player opponent,
        int rows,
        int columns
) {
}
