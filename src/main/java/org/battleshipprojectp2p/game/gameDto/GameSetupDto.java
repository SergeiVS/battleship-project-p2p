package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.game.Player;

public record GameSetupDto(
        Player localPlayer,
        Player remotePlayer,
        int rows,
        int columns
) {
}
