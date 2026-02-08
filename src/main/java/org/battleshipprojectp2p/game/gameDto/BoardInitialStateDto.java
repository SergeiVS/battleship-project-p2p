package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.common.CellValue;
import org.battleshipprojectp2p.game.board.PlayerBoard;
import org.battleshipprojectp2p.game.board.BoardCell;

import java.util.Arrays;

public record BoardInitialStateDto(
        CellValue[] boardArray
) {
    public BoardInitialStateDto(PlayerBoard playerBoard) {
        this(Arrays.stream(playerBoard.getBoard())
                .map(BoardCell::getCellValue)
                .toArray(CellValue[]::new)
        );
    }
}
