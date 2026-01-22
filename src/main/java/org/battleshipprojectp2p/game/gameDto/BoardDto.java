package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.game.Player;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.board.BoardCell;

public record BoardDto(
        PlayerDto player,
        int rowsCount,
        int columnsCount,
        BoardCell[] board
) {
    public BoardDto(Board board) {
        this(
                new PlayerDto(board.getBoardOwner()),
                board.getRowsCount(),
                board.getColumnsCount(),
                board.getBoard()
        );
    }
}
