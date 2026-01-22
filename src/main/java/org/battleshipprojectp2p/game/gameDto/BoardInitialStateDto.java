package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.board.BoardCell;

import java.util.Arrays;

public record BoardInitialStateDto(
        CellValue[] boardArray
) {
    public BoardInitialStateDto(Board board) {
        this(Arrays.stream(board.getBoard())
                .map(BoardCell::getCellValue)
                .toArray(CellValue[]::new)
        );
    }
}
