package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.List;

public record BoardData(
        Player player,
        int rowsCount,
        int colsCount,
        BoardCell[][] board,
        List<Ship> fleet
) {
    public BoardData(Board board) {
        int rowsCount = board.getRowsCount();
        int columnsCount = board.getColumnsCount();
        BoardCell[][] board2D = board.getBoard2D();


        this(
                board.getOwner(),
                rowsCount,
                columnsCount,
                board2D,
                board.getFleet()
        );
    }


}
