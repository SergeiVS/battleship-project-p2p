package org.battleshipprojectp2p.game.gameDto;

import org.battleshipprojectp2p.game.board.Board;
import org.battleshipprojectp2p.game.board.BoardInterface;

public record GameSetupResponse(
        BoardDto localBoard,
        BoardDto remoteBoard
) {
}
