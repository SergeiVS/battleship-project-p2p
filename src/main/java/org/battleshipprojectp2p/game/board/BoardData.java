package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.List;

public record BoardData(
        Player player,
        int rowsCount,
        int columnsCount,
        BoardCell[] board,
        List<Ship> fleet
) {
    public BoardData(Board board) {
        this(
                board.getOwner(),
                board.getRowsCount(),
                board.getColumnsCount(),
                board.getBoard(),
                board.getFleet()
        );
    }
}
