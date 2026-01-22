package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.game.Player;

public record PlayerDto(
        String name,
        boolean isLocal
) {
    public PlayerDto(Player player) {
        this(
                player.getName(),
                player.isLocal()
        );
    }
}
