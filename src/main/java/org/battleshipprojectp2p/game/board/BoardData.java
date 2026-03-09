package org.battleshipprojectp2p.game.board;

import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.game.ship.Ship;

import java.util.List;

public record BoardData(
        Player player,
        int rowsCount,
        int columnsCount,
        BoardCell[][] board,
        List<Ship> fleet
) {
    public BoardData(Board board) {
        int rowsCount = board.getRowsCount();
        int columnsCount = board.getColumnsCount();
        BoardCell[] boardCells = board.getBoard();
        BoardCell[][] board2D = new BoardCell[rowsCount][columnsCount];

        for (int x = 0; x < rowsCount; x++) {
            for (int y = 0; y < columnsCount; y++) {
                board2D[x][y] = boardCells[x * rowsCount + y];
            }
        }

        this(
                board.getOwner(),
                rowsCount,
                columnsCount,
                board2D,
                board.getFleet()
        );
    }


}
